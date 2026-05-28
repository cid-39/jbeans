import React, { useEffect, useState } from 'react';
import { narudzbinaService, pretplataService, Narudzbina, Pretplata } from '../../api/narudzbineService';

export const AdminOrders: React.FC = () => {
    const [narudzbine, setNarudzbine] = useState<Narudzbina[]>([]);
    const [pretplate, setPretplate] = useState<Pretplata[]>([]);
    const [selected, setSelected] = useState<Narudzbina | Pretplata | null>(null);
    const [type, setType] = useState<'NARUDZBINA' | 'PRETPLATA' | null>(null);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        const [n, p] = await Promise.all([
            narudzbinaService.getAllNarudzbine(),
            pretplataService.getAllPretplate()
        ]);
        setNarudzbine(n);
        setPretplate(p);
    };

    const openModal = (item: Narudzbina | Pretplata, t: 'NARUDZBINA' | 'PRETPLATA') => {
        setSelected(item);
        setType(t);
    };

    return (
        <div className="container" style={{ padding: '20px' }}>
            <h1 style={{ marginBottom: '30px', color: '#333' }}>Narudžbine i Pretplate</h1>
            <div style={{ display: 'flex', gap: '40px', marginTop: '20px' }}>

                {/* Tabela Narudžbine */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <h2 style={{ margin: '0 0 20px 0', fontSize: '20px', color: '#444' }}>Narudžbine</h2>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '20%' }}>ID</th>
                                <th style={{ padding: '12px 10px', width: '50%' }}>Kupac</th>
                                <th style={{ padding: '12px 10px', width: '30%' }}>Akcija</th>
                            </tr>
                        </thead>
                        <tbody>
                            {narudzbine.map(n => (
                                <tr key={n.id} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px' }}>#{n.id}</td>
                                    <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>#{n.klijent.id} - {n.klijent.username}</td>
                                    <td style={{ padding: '12px 10px' }}>
                                        <button 
                                            style={{ padding: '5px 10px', background: '#ffc107', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#000' }}
                                            onClick={() => openModal(n, 'NARUDZBINA')}
                                        >
                                            Detalji
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>

                {/* Tabela Pretplate */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <h2 style={{ margin: '0 0 20px 0', fontSize: '20px', color: '#444' }}>Pretplate</h2>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '20%' }}>ID</th>
                                <th style={{ padding: '12px 10px', width: '50%' }}>Period (dana)</th>
                                <th style={{ padding: '12px 10px', width: '30%' }}>Akcija</th>
                            </tr>
                        </thead>
                        <tbody>
                            {pretplate.map(p => (
                                <tr key={p.id} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px' }}>#{p.id}</td>
                                    <td style={{ padding: '12px 10px' }}>svakih {p.period} dana</td>
                                    <td style={{ padding: '12px 10px' }}>
                                        <button 
                                            style={{ padding: '5px 10px', background: '#ffc107', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#000' }}
                                            onClick={() => openModal(p, 'PRETPLATA')}
                                        >
                                            Detalji
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            </div>

            {/* Modal */}
            {selected && (
                <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={() => setSelected(null)}>
                    <div style={{ background: '#fff', padding: '30px', borderRadius: '8px', maxWidth: '600px', width: '90%', maxHeight: '80vh', overflowY: 'auto' }} onClick={e => e.stopPropagation()}>

                        {type === 'NARUDZBINA' ? (
                            // PRIKAZ NARUDŽBINE
                            <div className="order-details">
                                <h2 style={{ margin: '0 0 20px 0', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>Narudžbina #{(selected as Narudzbina).id}</h2>
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '20px' }}>
                                    <p style={{ margin: 0 }}><strong>Kupac:</strong> {(selected as Narudzbina).klijent.username}</p>
                                    <p style={{ margin: 0 }}><strong>Datum:</strong> {new Date((selected as Narudzbina).datumPorucivanja).toLocaleDateString('sr-RS')}</p>
                                    <p style={{ margin: 0 }}>
                                        <strong>Status dostave:</strong>{' '}
                                        <span style={{ 
                                            padding: '4px 8px', 
                                            borderRadius: '4px', 
                                            backgroundColor: (selected as Narudzbina).dostavljanje.status === 'ISPORUCENO' ? '#d4edda' : '#fff3cd', 
                                            color: (selected as Narudzbina).dostavljanje.status === 'ISPORUCENO' ? '#155724' : '#856404',
                                            fontSize: '13px',
                                            fontWeight: 'bold'
                                        }}>
                                            {(selected as Narudzbina).dostavljanje.status}
                                        </span>
                                    </p>
                                </div>

                                <h4 style={{ margin: '20px 0 10px 0', color: '#555' }}>Stavke:</h4>
                                <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px', marginBottom: '15px' }}>
                                    <thead>
                                        <tr style={{ background: '#f0f0f0', borderBottom: '1px solid #ccc', textAlign: 'left' }}>
                                            <th style={{ padding: '8px' }}>Proizvod</th>
                                            <th style={{ padding: '8px', width: '15%' }}>Kol.</th>
                                            <th style={{ padding: '8px', width: '25%' }}>Cena</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {(selected as Narudzbina).stavke.map(s => (
                                            <tr key={s.id} style={{ borderBottom: '1px solid #eee' }}>
                                                <td style={{ padding: '8px' }}>{s.proizvod.zrna.naziv} ({s.proizvod.tipPrzenja.naziv})</td>
                                                <td style={{ padding: '8px' }}>{s.kolicina}</td>
                                                <td style={{ padding: '8px' }}>{s.cena} RSD</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                                <p style={{ marginTop: '15px', fontWeight: 'bold', fontSize: '16px', textAlign: 'right', color: '#333' }}>Ukupno: {(selected as Narudzbina).ukupnaCena} RSD</p>
                            </div>
                        ) : (
                            // PRIKAZ PRETPLATE
                            <div className="sub-details">
                                <h2 style={{ margin: '0 0 20px 0', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>Pretplata #{(selected as Pretplata).id}</h2>
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '20px' }}>
                                    <p style={{ margin: 0 }}><strong>Period:</strong> svakih {(selected as Pretplata).period} dana</p>
                                    <p style={{ margin: 0 }}>
                                        <strong>Status:</strong>{' '}
                                        <span style={{ 
                                            padding: '4px 8px', 
                                            borderRadius: '4px', 
                                            backgroundColor: (selected as Pretplata).aktivna ? '#d4edda' : '#f8d7da', 
                                            color: (selected as Pretplata).aktivna ? '#155724' : '#721c24',
                                            fontSize: '13px',
                                            fontWeight: 'bold'
                                        }}>
                                            {(selected as Pretplata).aktivna ? 'Aktivna' : 'Neaktivna'}
                                        </span>
                                    </p>
                                </div>

                                <h4 style={{ margin: '20px 0 10px 0', color: '#555' }}>Istorija narudžbina u pretplati:</h4>
                                <ul style={{ paddingLeft: '20px', margin: 0, color: '#444', lineHeight: '1.6' }}>
                                    {(selected as Pretplata).narudzbine.map(n => (
                                        <li key={n.id}>Narudžbina #{n.id} — {new Date(n.datumPorucivanja).toLocaleDateString('sr-RS')}</li>
                                    ))}
                                </ul>
                            </div>
                        )}

                        <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '25px', borderTop: '1px solid #eee', paddingTop: '15px' }}>
                            <button 
                                style={{ padding: '8px 20px', background: '#ccc', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }} 
                                onClick={() => setSelected(null)}
                            >
                                Zatvori
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};