import React, { useEffect, useState } from 'react';
import apiClient from '../../api/apiClient';
import './Orders.css';

interface NarudzbinaSummary {
    id: number;
    datumPorucivanja: string;
    ukupnaCena: number;
    dostavljanje: {
        datumDostave: string;
        status: 'CEKA' | 'ISPORUCENO';
    };
}

export const MyOrders: React.FC = () => {
    const [orders, setOrders] = useState<NarudzbinaSummary[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    
    // Stanja za modal i detalje porudžbine
    const [selectedOrder, setSelectedOrder] = useState<any | null>(null);
    const [modalLoading, setModalLoading] = useState<boolean>(false);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

    useEffect(() => {
        const fetchMyOrders = async () => {
            try {
                const response = await apiClient.get<NarudzbinaSummary[]>('/narudzbina/moja');
                const sortedOrders = response.data.sort((a, b) => b.id - a.id);
                setOrders(sortedOrders);
            } catch (error) {
                console.error('Došlo je do greške prilikom preuzimanja porudžbina.', error);
            } finally {
                setLoading(false);
            }
        };

        fetchMyOrders();
    }, []);

    const handleViewDetails = async (id: number) => {
        setModalLoading(true);
        setIsModalOpen(true);
        try {
            const response = await apiClient.get<any>(`/narudzbina/get?id=${id}`);
            if (response.data) {
                setSelectedOrder(response.data);
            } else {
                alert('Porudžbina nema dostupnih detalja.');
                setIsModalOpen(false);
            }
        } catch (error) {
            console.error('Greška pri učitavanju detalja porudžbine.', error);
            alert('Nije moguće učitati detalje porudžbine.');
            setIsModalOpen(false);
        } finally {
            setModalLoading(false);
        }
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedOrder(null);
    };

    const formatFullDate = (dateString: string) => {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('sr-RS', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    // Računanje ukupne težine kafe u izabranoj porudžbini za potrebe provere dostave
    const izracunajUkupnuTezinu = (stavke: any[]) => {
        if (!stavke) return 0;
        return stavke.reduce((sum, item) => sum + (item.kolicina || 0), 0);
    };

    if (loading) {
        return <div className="container orders-layout">Učitavanje istorije porudžbina...</div>;
    }

    return (
        <div className="container orders-layout">
            <h1 className="orders-title">Moje Porudžbine</h1>

            {orders.length === 0 ? (
                <div className="empty-basket-msg">
                    <h2 style={{ color: 'var(--color-text-muted)' }}>Nemate prethodnih porudžbina.</h2>
                    <button onClick={() => window.location.href = '/'} style={{ marginTop: '15px' }}>
                        Započni Kupovinu
                    </button>
                </div>
            ) : (
                <div className="basket-table-container">
                    <table className="basket-table">
                        <thead>
                            <tr>
                                <th>ID Porudžbine</th>
                                <th>Datum Poručivanja</th>
                                <th>Planirana Isporuka</th>
                                <th>Ukupna Cena</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {orders.map((order) => (
                                <tr key={order.id}>
                                    <td>#JB-{order.id}</td>
                                    <td>{formatFullDate(order.datumPorucivanja)}</td>
                                    <td>{order.dostavljanje?.datumDostave || 'Nije definisano'}</td>
                                    <td style={{ fontWeight: 'bold' }}>{order.ukupnaCena.toLocaleString('sr-RS')} RSD</td>
                                    <td>
                                        <span className={`status-badge ${order.dostavljanje?.status?.toLowerCase() || 'ceka'}`}>
                                            {order.dostavljanje?.status === 'CEKA' ? 'Čeka prženje' : 'Isporučeno'}
                                        </span>
                                    </td>
                                    <td>
                                        <button onClick={() => handleViewDetails(order.id)}>
                                            Detalji
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {/* MODAL PROZOR ZA DETALJE */}
            {isModalOpen && (
                <div className="modal-overlay" style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={closeModal}>
                    <div className="modal-content" style={{ background: '#fff', padding: '30px', borderRadius: '8px', width: '100%', maxWidth: '600px', boxShadow: '0 4px 15px rgba(0,0,0,0.2)', position: 'relative', maxHeight: '90vh', overflowY: 'auto' }} onClick={(e) => e.stopPropagation()}>
                        
                        <button onClick={closeModal} style={{ position: 'absolute', top: '15px', right: '15px', background: 'none', border: 'none', fontSize: '24px', cursor: 'pointer', color: '#666' }}>&times;</button>
                        
                        {modalLoading ? (
                            <div style={{ textAlign: 'center', padding: '20px' }}>Učitavanje detalja...</div>
                        ) : selectedOrder ? (
                            <div>
                                <h2 style={{ color: 'var(--color-primary)', marginBottom: '15px', borderBottom: '2px solid #f4f4f4', paddingBottom: '10px' }}>
                                    Detalji Porudžbine #JB-{selectedOrder.id}
                                </h2>
                                
                                <div style={{ marginBottom: '20px', fontSize: '14px', display: 'flex', flexDirection: 'column', gap: '6px' }}>
                                    <p style={{ margin: 0 }}><strong>Datum poručivanja:</strong> {formatFullDate(selectedOrder.datumPorucivanja)}</p>
                                    <p style={{ margin: 0 }}><strong>Datum dostave:</strong> {selectedOrder.dostavljanje?.datumDostave}</p>
                                    <p style={{ margin: 0 }}><strong>Adresa isporuke:</strong> {selectedOrder.klijent?.adresa}</p>
                                    <p style={{ margin: 0 }}><strong>Status:</strong> <span style={{ fontWeight: 'bold', color: selectedOrder.dostavljanje?.status === 'CEKA' ? '#7f6000' : 'green' }}>{selectedOrder.dostavljanje?.status === 'CEKA' ? 'Čeka prženje' : 'Isporučeno'}</span></p>
                                </div>

                                <h3 style={{ fontSize: '16px', marginBottom: '10px', color: '#555' }}>Stavke u porudžbini:</h3>
                                <div style={{ background: '#f9f9f9', borderRadius: '6px', padding: '10px' }}>
                                    {selectedOrder.stavke?.map((stavka: any) => {
                                        const nazivKafe = stavka.proizvod?.zrna?.naziv || stavka.proizvod?.naziv || 'Kafa';
                                        const tipPrzenja = stavka.proizvod?.tipPrzenja?.naziv || 'Srednje';
                                        
                                        return (
                                            <div key={stavka.id} style={{ display: 'flex', justifyContent: 'space-between', padding: '8px 0', borderBottom: '1px solid #eee' }}>
                                                <div>
                                                    <strong style={{ display: 'block' }}>{nazivKafe}</strong>
                                                    <span style={{ fontSize: '12px', color: 'var(--color-text-muted)' }}>{tipPrzenja} prženje</span>
                                                </div>
                                                <div style={{ textAlign: 'right' }}>
                                                    <span style={{ display: 'block', fontWeight: '500' }}>{stavka.kolicina.toFixed(2)} kg</span>
                                                    <span style={{ fontSize: '13px', color: '#666' }}>{stavka.cena.toLocaleString('sr-RS')} RSD</span>
                                                </div>
                                            </div>
                                        );
                                    })}
                                </div>

                                {/* Obračun i uslovni prikaz napomene za dostavu ispod 3kg */}
                                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end', marginTop: '20px', borderTop: '2px solid #f4f4f4', paddingTop: '15px' }}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%', fontSize: '18px', fontWeight: 'bold' }}>
                                        <span>Ukupno za uplatu:</span>
                                        <span style={{ color: 'var(--color-primary)' }}>{selectedOrder.ukupnaCena.toLocaleString('sr-RS')} RSD</span>
                                    </div>
                                    {izracunajUkupnuTezinu(selectedOrder.stavke) < 3 && (
                                        <span style={{ fontSize: '13px', color: '#bc6c25', fontWeight: '500', marginTop: '4px' }}>
                                            (+ 350 RSD dostava)
                                        </span>
                                    )}
                                </div>
                            </div>
                        ) : (
                            <div style={{ textAlign: 'center', padding: '20px', color: 'red' }}>Podaci nisu pronađeni.</div>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};