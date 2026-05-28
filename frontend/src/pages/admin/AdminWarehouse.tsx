import React, { useEffect, useState } from 'react';
import { magacinService, Dobavljac, SirovaZrna } from '../../api/magacinService';

export const AdminWarehouse: React.FC = () => {
    const [dobavljaci, setDobavljaci] = useState<Dobavljac[]>([]);
    const [zrna, setZrna] = useState<SirovaZrna[]>([]);
    
    // State za modale
    const [showDobModal, setShowDobModal] = useState(false);
    const [showZrnoModal, setShowZrnoModal] = useState(false);
    const [editData, setEditData] = useState<any>(null);

    useEffect(() => { loadData(); }, []);

    const loadData = async () => {
        const [d, z] = await Promise.all([magacinService.getAllDobavljaci(), magacinService.getAllZrna()]);
        setDobavljaci(d || []);
        setZrna(z || []);
    };

    // --- MODAL KOMPONENTE ---

    const DobavljacModal = () => (
        <div className="modal-overlay" style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={() => setShowDobModal(false)}>
            <div className="modal-content" style={{ background: '#fff', padding: '30px', borderRadius: '8px', width: '400px' }} onClick={e => e.stopPropagation()}>
                <h3 style={{ margin: '0 0 20px 0' }}>{editData ? "Izmeni dobavljača" : "Novi dobavljač"}</h3>
                <input type="text" placeholder="PIB" disabled={!!editData} defaultValue={editData?.pib} id="d-pib" style={{ width: '100%', padding: '10px', marginBottom: '15px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <input type="text" placeholder="Naziv" defaultValue={editData?.naziv} id="d-naziv" style={{ width: '100%', padding: '10px', marginBottom: '15px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <input type="text" placeholder="Telefon" defaultValue={editData?.brojTelefona} id="d-tel" style={{ width: '100%', padding: '10px', marginBottom: '20px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
                    <button style={{ padding: '8px 15px', background: '#ccc', border: 'none', borderRadius: '4px', cursor: 'pointer' }} onClick={() => setShowDobModal(false)}>Otkaži</button>
                    <button style={{ padding: '8px 15px', background: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }} onClick={async () => {
                        const data = { pib: (document.getElementById('d-pib') as HTMLInputElement).value, naziv: (document.getElementById('d-naziv') as HTMLInputElement).value, brojTelefona: (document.getElementById('d-tel') as HTMLInputElement).value };
                        editData ? await magacinService.updateDobavljac(data.pib, data) : await magacinService.createDobavljac(data);
                        setShowDobModal(false); loadData();
                    }}>Sačuvaj</button>
                </div>
            </div>
        </div>
    );

    const ZrnoModal = () => (
        <div className="modal-overlay" style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={() => setShowZrnoModal(false)}>
            <div className="modal-content" style={{ background: '#fff', padding: '30px', borderRadius: '8px', width: '400px' }} onClick={e => e.stopPropagation()}>
                <h3 style={{ margin: '0 0 20px 0' }}>{editData ? "Izmeni zrna" : "Dodaj zrna"}</h3>
                <input type="text" placeholder="Naziv zrna" defaultValue={editData?.naziv} id="z-naziv" style={{ width: '100%', padding: '10px', marginBottom: '15px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <input type="number" placeholder="Količina" defaultValue={editData?.kolicinaNaStanju} id="z-kol" style={{ width: '100%', padding: '10px', marginBottom: '15px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <input type="number" placeholder="Cena" defaultValue={editData?.cenaPoMeri} id="z-cena" style={{ width: '100%', padding: '10px', marginBottom: '15px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }} />
                <select defaultValue={editData?.dobavljac.pib} id="z-pib" style={{ width: '100%', padding: '10px', marginBottom: '20px', borderRadius: '4px', border: '1px solid #ccc', boxSizing: 'border-box' }}>
                    {dobavljaci.map(d => <option key={d.pib} value={d.pib}>{d.naziv}</option>)}
                </select>
                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
                    <button style={{ padding: '8px 15px', background: '#ccc', border: 'none', borderRadius: '4px', cursor: 'pointer' }} onClick={() => setShowZrnoModal(false)}>Otkaži</button>
                    <button style={{ padding: '8px 15px', background: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }} onClick={async () => {
                        const data = { naziv: (document.getElementById('z-naziv') as HTMLInputElement).value, kolicinaNaStanju: Number((document.getElementById('z-kol') as HTMLInputElement).value), cenaPoMeri: Number((document.getElementById('z-cena') as HTMLInputElement).value), dobavljac: { pib: (document.getElementById('z-pib') as HTMLInputElement).value } };
                        editData ? await magacinService.updateZrno(editData.id, data) : await magacinService.createZrno(data);
                        setShowZrnoModal(false); loadData();
                    }}>Sačuvaj</button>
                </div>
            </div>
        </div>
    );

    return (
        <div className="container" style={{ padding: '20px' }}>
            <h1 style={{ marginBottom: '30px', color: '#333' }}>Upravljanje Magacinom</h1>
            
            <div style={{ display: 'flex', gap: '40px' }}>
                {/* SEKCIJA DOBAVLJAČI */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                        <h2 style={{ margin: 0, fontSize: '20px', color: '#444' }}>Dobavljači</h2>
                        <button 
                            style={{ padding: '8px 16px', background: '#007bff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}
                            onClick={() => { setEditData(null); setShowDobModal(true); }}
                        >
                            + Dodaj Dobavljača
                        </button>
                    </div>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '60%' }}>Naziv</th>
                                <th style={{ padding: '12px 10px', width: '40%' }}>Akcije</th>
                            </tr>
                        </thead>
                        <tbody>
                            {dobavljaci.map(d => (
                                <tr key={d.pib} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{d.naziv}</td>
                                    <td style={{ padding: '12px 10px' }}>
                                        <button 
                                            style={{ marginRight: '8px', padding: '5px 10px', background: '#ffc107', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#000' }}
                                            onClick={() => { setEditData(d); setShowDobModal(true); }}
                                        >
                                            Izmeni
                                        </button>
                                        <button 
                                            style={{ padding: '5px 10px', background: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                            onClick={async () => { await magacinService.removeDobavljac(d.pib); loadData(); }}
                                        >
                                            Obriši
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>

                {/* SEKCIJA SIROVA ZRNA */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                        <h2 style={{ margin: 0, fontSize: '20px', color: '#444' }}>Sirova Zrna</h2>
                        <button 
                            style={{ padding: '8px 16px', background: '#28a745', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}
                            onClick={() => { setEditData(null); setShowZrnoModal(true); }}
                        >
                            + Dodaj Zrna
                        </button>
                    </div>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '35%' }}>Naziv</th>
                                <th style={{ padding: '12px 10px', width: '35%' }}>Dobavljač</th>
                                <th style={{ padding: '12px 10px', width: '30%' }}>Akcije</th>
                            </tr>
                        </thead>
                        <tbody>
                            {zrna.map(z => (
                                <tr key={z.id} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{z.naziv}</td>
                                    <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{z.dobavljac.naziv}</td>
                                    <td style={{ padding: '12px 10px' }}>
                                        <button 
                                            style={{ marginRight: '8px', padding: '5px 10px', background: '#ffc107', border: 'none', borderRadius: '4px', cursor: 'pointer', color: '#000' }}
                                            onClick={() => { setEditData(z); setShowZrnoModal(true); }}
                                        >
                                            Izmeni
                                        </button>
                                        <button 
                                            style={{ padding: '5px 10px', background: '#dc3545', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                            onClick={async () => { await magacinService.removeZrno(z.id!); loadData(); }}
                                        >
                                            Obriši
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            </div>

            {showDobModal && <DobavljacModal />}
            {showZrnoModal && <ZrnoModal />}
        </div>
    );
};