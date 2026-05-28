import React, { useEffect, useState } from 'react';
import { proizvodService, Proizvod } from '../../api/proizvodService';

export const AdminCatalog: React.FC = () => {
    const [proizvodi, setProizvodi] = useState<Proizvod[]>([]);
    const [selectedProizvod, setSelectedProizvod] = useState<Proizvod | null>(null);

    useEffect(() => {
        fetchProizvodi();
    }, []);

    const fetchProizvodi = async () => {
        const data = await proizvodService.getAllProizvodi();
        setProizvodi(data);
    };

    const handlePotvrdiPrzenje = async (id: number) => {
        await proizvodService.potvrdiPrzenje(id);
        fetchProizvodi();
    };

    return (
        <div className="container" style={{ padding: '20px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' }}>
                <h1 style={{ margin: 0, color: '#333' }}>Katalog Proizvoda</h1>
                <button 
                    style={{ padding: '10px 20px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}
                    onClick={() => window.location.href = '/admin/plan'}
                >
                    Prikaži Dnevni Plan
                </button>
            </div>

            <div style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                    <thead>
                        <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                            <th style={{ padding: '12px 10px', width: '15%' }}>Datum</th>
                            <th style={{ padding: '12px 10px', width: '20%' }}>Kafa</th>
                            <th style={{ padding: '12px 10px', width: '15%' }}>Tip Prženja</th>
                            <th style={{ padding: '12px 10px', width: '15%' }}>Količina (kg)</th>
                            <th style={{ padding: '12px 10px', width: '15%' }}>Status</th>
                            <th style={{ padding: '12px 10px', width: '20%' }}>Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        {proizvodi.map(p => (
                            <tr key={p.id} style={{ borderBottom: '1px solid #eee' }}>
                                <td style={{ padding: '12px 10px' }}>{new Date(p.datumPrzenja).toLocaleDateString('sr-RS')}</td>
                                <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{p.zrna.naziv}</td>
                                <td style={{ padding: '12px 10px' }}>{p.tipPrzenja.naziv}</td>
                                <td style={{ padding: '12px 10px' }}>{p.kolicinaPrzena.toFixed(2)}</td>
                                <td style={{ padding: '12px 10px' }}>
                                    <span style={{ 
                                        padding: '4px 8px', 
                                        borderRadius: '4px', 
                                        backgroundColor: p.opis === 'CEKA PRZENJE' ? '#fff3cd' : '#d4edda', 
                                        color: p.opis === 'CEKA PRZENJE' ? '#856404' : '#155724',
                                        fontSize: '13px',
                                        fontWeight: 'bold',
                                        display: 'inline-block'
                                    }}>
                                        {p.opis}
                                    </span>
                                </td>
                                <td style={{ padding: '12px 10px' }}>
                                    <button 
                                        style={{ padding: '5px 10px', background: '#ffc107', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#000', marginRight: p.opis === 'CEKA PRZENJE' ? '8px' : 0 }}
                                        onClick={() => setSelectedProizvod(p)}
                                    >
                                        Detalji
                                    </button>
                                    {p.opis === 'CEKA PRZENJE' && (
                                        <button 
                                            style={{ backgroundColor: '#28a745', color: 'white', border: 'none', padding: '5px 10px', cursor: 'pointer', borderRadius: '4px' }}
                                            onClick={() => handlePotvrdiPrzenje(p.id)}
                                        >
                                            Potvrdi prženje
                                        </button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {/* MODAL ZA DETALJE */}
            {selectedProizvod && (
                <div className="modal-overlay" style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={() => setSelectedProizvod(null)}>
                    <div className="modal-content" style={{ background: '#fff', padding: '30px', borderRadius: '8px', width: '400px' }} onClick={e => e.stopPropagation()}>
                        <h2 style={{ margin: '0 0 20px 0', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>Detalji proizvoda #{selectedProizvod.id}</h2>
                        
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px', marginBottom: '25px' }}>
                            <p style={{ margin: 0 }}><strong>Naziv zrna:</strong> {selectedProizvod.zrna.naziv}</p>
                            <p style={{ margin: 0 }}><strong>Dobavljač:</strong> {selectedProizvod.zrna.dobavljac.naziv}</p>
                            <p style={{ margin: 0 }}><strong>Tip prženja:</strong> {selectedProizvod.tipPrzenja.naziv}</p>
                            <p style={{ margin: 0 }}><strong>Količina:</strong> {selectedProizvod.kolicinaPrzena} kg</p>
                            <p style={{ margin: 0 }}><strong>Datum:</strong> {new Date(selectedProizvod.datumPrzenja).toLocaleDateString('sr-RS')}</p>
                            <p style={{ margin: 0 }}><strong>Opis:</strong> {selectedProizvod.opis}</p>
                        </div>
                        
                        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                            <button 
                                style={{ padding: '8px 15px', background: '#ccc', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                onClick={() => setSelectedProizvod(null)}
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