import React, { useEffect, useState } from 'react';
import apiClient from '../../api/apiClient';
import './Subscriptions.css';

interface Stavka {
    id: number;
    kolicina: number;
    cena: number;
    proizvod: {
        id: number;
        tipPrzenja: { id: number; naziv: string };
        zrna: { id: number; naziv: string; cenaPoMeri: number };
    };
}

interface Narudzbina {
    id: number;
    datumPorucivanja: string;
    ukupnaCena: number;
    dostavljanje: {
        id: number;
        datumDostave: string;
        status: 'CEKA' | 'ISPORUCENO';
    };
    stavke: Stavka[];
}

interface Pretplata {
    id: number;
    period: number;
    aktivna: boolean;
    narudzbine: Narudzbina[];
}

export const MySubscriptions: React.FC = () => {
    const [subscriptions, setSubscriptions] = useState<Pretplata[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    // Stanja za modal izmene perioda
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const [selectedSubId, setSelectedSubId] = useState<number | null>(null);
    const [newPeriod, setNewPeriod] = useState<number>(1);

    const fetchSubscriptions = async () => {
        try {
            const response = await apiClient.get<Pretplata[]>('/pretplata/moja');
            // Sortiramo pretplate tako da najnovije (sa većim ID-jem) budu prve
            // Prikazujemo samo one koje nisu obrisane/deaktivirane (aktivna: true) 
            // - ukoliko tvoj backend vraća i obrisane, ovde to možemo filtrirati:
            const aktivnePretplate = response.data.filter(sub => sub.aktivna);
            const sorted = aktivnePretplate.sort((a, b) => b.id - a.id);
            setSubscriptions(sorted);
        } catch (error) {
            console.error('Greška sa učitavanjem pretplata.', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchSubscriptions();
    }, []);

    // Otvaranje modala za izmenu
    const openEditModal = (id: number, currentPeriod: number) => {
        setSelectedSubId(id);
        setNewPeriod(currentPeriod);
        setIsModalOpen(true);
    };

    const closeEditModal = () => {
        setIsModalOpen(false);
        setSelectedSubId(null);
    };

    // Slanje zahteva za promenu perioda
    const handleSumbitNewPeriod = async () => {
        if (!selectedSubId || newPeriod <= 0) {
            alert('Molimo unesite validan broj dana (veći od 0).');
            return;
        }

        try {
            await apiClient.post(`/pretplata/update?id=${selectedSubId}`, {
                period: newPeriod
            });
            // alert('Period pretplate uspešno izmenjen!');
            closeEditModal();
            fetchSubscriptions();
        } catch (error) {
            console.error(error);
            alert('Greška prilikom izmene perioda pretplate.');
        }
    };

    // Brisanje (prekid) pretplate
    const handleDeleteSubscription = async (id: number) => {
        if (!window.confirm("Da li ste sigurni da želite trajno da prekinete ovu pretplatu?")) {
            return;
        }

        try {
            await apiClient.post(`/pretplata/delete?id=${id}`);
            // alert('Pretplata uspešno prekinuta.');
            fetchSubscriptions();
        } catch (error) {
            console.error(error);
            alert('Greška prilikom prekidanja pretplate.');
        }
    };

    if (loading) return <div className="container subs-layout">Učitavanje pretplatničkih profila...</div>;

    return (
        <div className="container subs-layout">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '24px' }}>
                <h1 style={{ color: 'var(--color-primary)' }}>Moje Aktivne Pretplate</h1>
                <button onClick={() => window.location.href = '/pretplate/nova'}>+ Nova Pretplata</button>
            </div>

            {subscriptions.length === 0 ? (
                <div className="empty-basket-msg">
                    <h2 style={{ color: 'var(--color-text-muted)' }}>Nemate aktiviranih auto-isporuka.</h2>
                    <p style={{ margin: '10px 0 15px 0' }}>Aktivirajte pretplatu i osigurajte da zalihe sveže pržene kafe nikada ne ponestanu.</p>
                    <button onClick={() => window.location.href = '/pretplate/nova'}>Kreiraj prvu pretplatu</button>
                </div>
            ) : (
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(360px, 1fr))', gap: '20px' }}>
                    {subscriptions.map((sub) => {
                        const referentnaNarudzbina = sub.narudzbine && sub.narudzbine.length > 0 ? sub.narudzbine[0] : null;
                        const prvaStavka = referentnaNarudzbina && referentnaNarudzbina.stavke?.length > 0 ? referentnaNarudzbina.stavke[0] : null;
                        
                        const nazivKafe = prvaStavka?.proizvod?.zrna?.naziv || 'Kafa';
                        const tipPrzenja = prvaStavka?.proizvod?.tipPrzenja?.naziv || 'Srednje';
                        const kolicina = prvaStavka?.kolicina || 0;
                        const ukupnaCena = referentnaNarudzbina?.ukupnaCena || 0;

                        return (
                            <div className="sub-card" key={sub.id} style={{ padding: '20px', border: '1px solid #eee', borderRadius: '8px', background: '#fff', boxShadow: '0 2px 5px rgba(0,0,0,0.05)' }}>
                                <div className="sub-card-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '12px' }}>
                                    <div>
                                        <span style={{ fontSize: '12px', color: '#999', display: 'block' }}>Pretplata #PT-{sub.id}</span>
                                        <strong style={{ fontSize: '20px', color: 'var(--color-primary)' }}>{nazivKafe}</strong>
                                    </div>
                                    {/* ISTAKNUTI BEDŽ ZA DANE */}
                                    <span style={{ background: 'var(--color-primary)', color: '#fff', padding: '6px 10px', borderRadius: '4px', fontSize: '13px', fontWeight: 'bold', boxShadow: '0 1px 3px rgba(0,0,0,0.2)' }}>
                                        Svakih {sub.period} dana
                                    </span>
                                </div>

                                <div style={{ borderTop: '1px solid #f9f9f9', borderBottom: '1px solid #f9f9f9', padding: '10px 0', margin: '10px 0', fontSize: '14px', display: 'flex', flexDirection: 'column', gap: '6px' }}>
                                    <p style={{ margin: 0 }}>Profil prženja: <strong>{tipPrzenja}</strong></p>
                                    <p style={{ margin: 0 }}>Količina po isporuci: <strong>{kolicina.toFixed(2)} kg</strong></p>
                                    <p style={{ margin: 0 }}>Cena po isporuci: <strong>{ukupnaCena.toLocaleString('sr-RS')} RSD</strong> {kolicina < 3 && <span style={{ fontSize: '12px', color: '#bc6c25' }}>(+350 RSD dostava)</span>}</p>
                                    <p style={{ margin: 0 }}>Broj generisanih isporuka: <strong>{sub.narudzbine?.length || 0}</strong></p>
                                </div>

                                <div className="sub-actions-row" style={{ marginTop: '15px', display: 'flex', gap: '10px' }}>
                                    <button 
                                        onClick={() => openEditModal(sub.id, sub.period)} 
                                        style={{ flex: 1, backgroundColor: '#f4f4f4', color: '#333', border: '1px solid #ddd', padding: '8px 12px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}
                                    >
                                        Izmeni period
                                    </button>
                                    <button 
                                        onClick={() => handleDeleteSubscription(sub.id)} 
                                        style={{ flex: 1, backgroundColor: '#dc3545', color: '#fff', border: 'none', padding: '8px 12px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}
                                    >
                                        Prekini
                                    </button>
                                </div>
                            </div>
                        );
                    })}
                </div>
            )}

            {/* MODAL ZA IZMENU PERIODA */}
            {isModalOpen && (
                <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={closeEditModal}>
                    <div style={{ background: '#fff', padding: '25px', borderRadius: '8px', width: '90%', maxWidth: '400px', boxShadow: '0 4px 15px rgba(0,0,0,0.2)' }} onClick={(e) => e.stopPropagation()}>
                        <h3 style={{ marginTop: 0, color: 'var(--color-primary)', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>Izmena perioda isporuke</h3>
                        
                        <div style={{ margin: '20px 0' }}>
                            <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold', fontSize: '14px' }}>Nova učestalost isporuke (u danima):</label>
                            <input 
                                type="number" 
                                min="1" 
                                step="1" 
                                value={newPeriod} 
                                onChange={(e) => setNewPeriod(parseInt(e.target.value) || 1)}
                                style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc', fontSize: '16px' }}
                            />
                        </div>

                        <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                            <button onClick={closeEditModal} style={{ padding: '8px 16px', background: '#f4f4f4', border: '1px solid #ddd', borderRadius: '4px', cursor: 'pointer', color: '#333' }}>Odustani</button>
                            <button onClick={handleSumbitNewPeriod} style={{ padding: '8px 16px', background: 'var(--color-primary)', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#fff', fontWeight: 'bold' }}>Sačuvaj</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};