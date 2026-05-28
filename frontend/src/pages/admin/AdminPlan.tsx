import React, { useEffect, useState } from 'react';
import { proizvodService, DnevniPlan } from '../../api/proizvodService';

export const AdminPlan: React.FC = () => {
    const [plan, setPlan] = useState<DnevniPlan[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        fetchPlan();
    }, []);

    const fetchPlan = async () => {
        try {
            const data = await proizvodService.getDnevniSpisak();
            setPlan(data);
        } catch (error) {
            console.error("Greška pri učitavanju dnevnog plana", error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container" style={{ padding: '20px' }}>
            {/* Dugme nazad */}
            <button 
                onClick={() => window.location.href = '/admin/katalog'} 
                style={{ marginBottom: '20px', padding: '8px 16px', cursor: 'pointer' }}
            >
                ← Nazad na Katalog
            </button>

            <h1>Dnevni Plan Prženja</h1>

            {loading ? (
                <p>Učitavanje plana...</p>
            ) : plan.length === 0 ? (
                <p>Nema planiranih prženja za danas.</p>
            ) : (
                <table style={{ width: '100%', marginTop: '20px', borderCollapse: 'collapse' }}>
                    <thead>
                        <tr style={{ borderBottom: '2px solid #ccc', textAlign: 'left' }}>
                            <th style={{ padding: '10px' }}>Naziv Zrna</th>
                            <th style={{ padding: '10px' }}>Tip Prženja</th>
                            <th style={{ padding: '10px' }}>Količina (kg)</th>
                            <th style={{ padding: '10px' }}>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        {plan.map((item, index) => (
                            <tr key={index} style={{ borderBottom: '1px solid #eee' }}>
                                <td style={{ padding: '10px' }}>{item.nazivZrna}</td>
                                <td style={{ padding: '10px' }}>{item.tipPrzenja}</td>
                                <td style={{ padding: '10px' }}>{item.kolicina.toFixed(2)}</td>
                                <td style={{ padding: '10px' }}>
                                    <span style={{ 
                                        padding: '4px 8px', 
                                        borderRadius: '4px', 
                                        backgroundColor: '#fff3cd', 
                                        color: '#856404' 
                                    }}>
                                        {item.opis}
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};