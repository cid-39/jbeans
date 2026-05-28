import React, { useEffect, useState } from 'react';
import { dostavaService, Dostava, Vozilo } from '../../api/dostavaService';

export const AdminDeliveries: React.FC = () => {
    const [dostave, setDostave] = useState<Dostava[]>([]);
    const [vozila, setVozila] = useState<Vozilo[]>([]);
    const [selectedDostava, setSelectedDostava] = useState<Dostava | null>(null);
    const [odabranaRegistracija, setOdabranaRegistracija] = useState<string>('');

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const [dData, vData] = await Promise.all([
                dostavaService.getAllDostave(),
                dostavaService.getAllVozila()
            ]);
            setDostave(dData);
            setVozila(vData);
        } catch (error) {
            console.error("Greška prilikom učitavanja podataka:", error);
        }
    };

    const handleOtvoriModal = (dostava: Dostava) => {
        setSelectedDostava(dostava);
        setOdabranaRegistracija(dostava.vozilo?.registracija || '');
    };

    const handleSacuvajVozilo = async () => {
        if (!selectedDostava || !odabranaRegistracija) return;
        try {
            await dostavaService.dodeliVozilo(selectedDostava.id, odabranaRegistracija);
            setSelectedDostava(null);
            fetchData();
        } catch (error) {
            console.error("Greška prilikom dodeljivanja vozila", error);
        }
    };

    const handlePromeniStatus = async (id: number, ishod: string) => {
        try {
            await dostavaService.updateStatus(id, ishod);
            fetchData();
        } catch (error) {
            console.error("Greška prilikom ažuriranja statusa dostave", error);
        }
    };

    const dostavePoDanima = dostave.reduce((acc: { [key: string]: Dostava[] }, dostava) => {
        const datum = new Date(dostava.datumDostave).toLocaleDateString('sr-RS');
        if (!acc[datum]) {
            acc[datum] = [];
        }
        acc[datum].push(dostava);
        return acc;
    }, {});

    return (
        <div className="container" style={{ padding: '20px' }}>
            <h1>Upravljanje Dostavama</h1>

            {Object.keys(dostavePoDanima).length === 0 ? (
                <p style={{ marginTop: '20px' }}>Trenutno nema evidentiranih dostava.</p>
            ) : (
                Object.keys(dostavePoDanima).sort().map(datum => (
                    <div key={datum} style={{ marginTop: '30px', border: '1px solid #ddd', borderRadius: '8px', padding: '15px', background: '#fafafa' }}>
                        <h3 style={{ margin: '0 0 15px 0', color: '#333' }}>Datum dostave: {datum}</h3>
                        
                        <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                            <thead>
                                <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                    <th style={{ padding: '10px', width: '15%' }}>ID Dostave</th>
                                    <th style={{ padding: '10px', width: '35%' }}>Vozilo</th>
                                    <th style={{ padding: '10px', width: '20%' }}>Status</th>
                                    <th style={{ padding: '10px', width: '30%' }}>Akcije</th>
                                </tr>
                            </thead>
                            <tbody>
                                {dostavePoDanima[datum].map(d => (
                                    <tr key={d.id} style={{ borderBottom: '1px solid #eee' }}>
                                        <td style={{ padding: '10px' }}>#{d.id}</td>
                                        <td style={{ padding: '10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>
                                            {d.vozilo ? `${d.vozilo.registracija} ${d.vozilo.tipVozila?.naziv ? `(${d.vozilo.tipVozila.naziv})` : ''}` : 'Nije dodeljeno'}
                                        </td>
                                        <td style={{ padding: '10px' }}>
                                            <span style={{ 
                                                padding: '4px 8px', 
                                                borderRadius: '4px', 
                                                backgroundColor: d.status === 'ISPORUCENO' ? '#d4edda' : '#fff3cd', 
                                                color: d.status === 'ISPORUCENO' ? '#155724' : '#856404',
                                                display: 'inline-block'
                                            }}>
                                                {d.status}
                                            </span>
                                        </td>
                                        <td style={{ padding: '10px' }}>
                                            <button 
                                                style={{ marginRight: '10px', padding: '6px 12px', cursor: 'pointer' }} 
                                                onClick={() => handleOtvoriModal(d)}
                                            >
                                                Odaberi vozilo
                                            </button>
                                            {d.status !== 'ISPORUCENO' && (
                                                <button 
                                                    style={{ backgroundColor: '#28a745', color: 'white', border: 'none', padding: '6px 12px', cursor: 'pointer', borderRadius: '4px' }}
                                                    onClick={() => handlePromeniStatus(d.id, 'ISPORUCENO')}
                                                >
                                                    Onači kao isporučeno
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ))
            )}

            {/* MODAL ZA ODABIR VOZILA */}
            {selectedDostava && (
                <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }} onClick={() => setSelectedDostava(null)}>
                    <div style={{ background: '#fff', padding: '30px', borderRadius: '8px', width: '400px' }} onClick={e => e.stopPropagation()}>
                        <h2>Dodeli vozilo za dostavu #{selectedDostava.id}</h2>
                        <p style={{ fontSize: '14px', color: '#666' }}>Datum: {new Date(selectedDostava.datumDostave).toLocaleDateString('sr-RS')}</p>
                        
                        <div style={{ margin: '20px 0' }}>
                            <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>Izaberi vozilo:</label>
                            <select 
                                style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #ccc' }}
                                value={odabranaRegistracija}
                                onChange={e => setOdabranaRegistracija(e.target.value)}
                            >
                                <option value="">-- Selektuj vozilo sa spiska --</option>
                                {vozila.map(v => (
                                    <option key={v.registracija} value={v.registracija}>
                                        {v.registracija} {v.tipVozila ? `[${v.tipVozila.naziv}]` : ''}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
                            <button style={{ padding: '8px 15px', background: '#ccc', border: 'none', borderRadius: '4px', cursor: 'pointer' }} onClick={() => setSelectedDostava(null)}>
                                Otkaži
                            </button>
                            <button 
                                style={{ padding: '8px 15px', background: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                                onClick={handleSacuvajVozilo}
                                disabled={!odabranaRegistracija}
                            >
                                Sačuvaj
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};