import React, { useEffect, useState } from 'react';
import apiClient from '../../api/apiClient';

// --- INTERFEJSI ZA PODATKE ---
interface OpterecenostDostave {
    brojDostava: number;
    datum: string;
}

interface PopularnoZrno {
    naziv: string;
    ukupnaKolicina: number;
}

interface TopDobavljac {
    naziv: string;
    ukupanPrihod: number;
}

interface TopKlijent {
    brojNarudzbina: number;
    email: string;
    ukupnaPotrosnja: number;
}

interface TrendPrzenja {
    naziv: string;
    ukupnaKolicina: number;
}

interface AnalitikaData {
    opterecenostDostave: OpterecenostDostave[];
    popularnijaZrna: PopularnoZrno[];
    prosecnaVrednostKorpe: number;
    topDobavljaci: TopDobavljac[];
    topKlijenti: TopKlijent[];
    trendoviPrzenja: TrendPrzenja[];
    ukupanPrihod: number;
}

export const AdminAnalytics: React.FC = () => {
    const [data, setData] = useState<AnalitikaData | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchAnalitika = async () => {
            try {
                const response = await apiClient.get<AnalitikaData>('/analitika');
                setData(response.data);
            } catch (error) {
                console.error("Greška prilikom učitavanja analitike:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchAnalitika();
    }, []);

    if (loading) {
        return <div style={{ padding: '20px' }}>Učitavanje analitike...</div>;
    }

    if (!data) {
        return <div style={{ padding: '20px' }}>Nije moguće učitati podatke analitike.</div>;
    }

    // Pomoćne vrijednosti za skaliranje grafikona
    const maxDostava = Math.max(...data.opterecenostDostave.map(d => d.brojDostava), 1);
    const maxKafa = Math.max(...data.popularnijaZrna.map(z => z.ukupnaKolicina), 1);
    const maxPrzenje = Math.max(...data.trendoviPrzenja.map(t => t.ukupnaKolicina), 1);

    return (
        <div className="container" style={{ padding: '20px' }}>
            <h1 style={{ marginBottom: '30px', color: '#333' }}>Poslovna Analitika</h1>

            {/* KARTICE SA GLAVNIM METRIKAMA */}
            <div style={{ display: 'flex', gap: '20px', marginBottom: '30px' }}>
                <div style={{ flex: 1, background: '#fafafa', border: '1px solid #ddd', borderRadius: '8px', padding: '20px', textAlign: 'center' }}>
                    <h3 style={{ margin: '0 0 10px 0', color: '#666', fontSize: '16px' }}>Ukupan Prihod</h3>
                    <p style={{ margin: 0, fontSize: '28px', fontWeight: 'bold', color: '#28a745' }}>{data.ukupanPrihod.toLocaleString('sr-RS')} RSD</p>
                </div>
                <div style={{ flex: 1, background: '#fafafa', border: '1px solid #ddd', borderRadius: '8px', padding: '20px', textAlign: 'center' }}>
                    <h3 style={{ margin: '0 0 10px 0', color: '#666', fontSize: '16px' }}>Prosečna Korpa</h3>
                    <p style={{ margin: 0, fontSize: '28px', fontWeight: 'bold', color: '#007bff' }}>{data.prosecnaVrednostKorpe.toFixed(2)} RSD</p>
                </div>
            </div>

            {/* TABELE: KLIJENTI I DOBAVLJAČI */}
            <div style={{ display: 'flex', gap: '40px', marginBottom: '40px' }}>
                {/* Top Klijenti */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <h2 style={{ margin: '0 0 20px 0', fontSize: '18px', color: '#444' }}>Top Klijenti</h2>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '50%' }}>Email</th>
                                <th style={{ padding: '12px 10px', width: '25%' }}>Porudžbine</th>
                                <th style={{ padding: '12px 10px', width: '25%' }}>Potrošnja</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data.topKlijenti.map((k, index) => (
                                <tr key={index} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px', textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}>{k.email}</td>
                                    <td style={{ padding: '12px 10px' }}>{k.brojNarudzbina}</td>
                                    <td style={{ padding: '12px 10px', fontWeight: 'bold' }}>{k.ukupnaPotrosnja} RSD</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>

                {/* Top Dobavljači */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <h2 style={{ margin: '0 0 20px 0', fontSize: '18px', color: '#444' }}>Top Dobavljači</h2>
                    <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', tableLayout: 'fixed' }}>
                        <thead>
                            <tr style={{ borderBottom: '2px solid #ccc', background: '#f0f0f0', textAlign: 'left' }}>
                                <th style={{ padding: '12px 10px', width: '60%' }}>Dobavljač</th>
                                <th style={{ padding: '12px 10px', width: '40%' }}>Ukupan Prihod</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data.topDobavljaci.map((d, index) => (
                                <tr key={index} style={{ borderBottom: '1px solid #eee' }}>
                                    <td style={{ padding: '12px 10px' }}>{d.naziv}</td>
                                    <td style={{ padding: '12px 10px', fontWeight: 'bold' }}>{d.ukupanPrihod} RSD</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </section>
            </div>

            {/* GRAFIKONI I PRODAJNI TRENDOVI */}
            <div style={{ display: 'flex', gap: '40px' }}>
                {/* Popularnost kafe i trendovi prženja */}
                <div style={{ flex: 1, display: 'flex', flexDirection: 'column', gap: '30px' }}>
                    
                    {/* Popularnost zrna */}
                    <section style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                        <h2 style={{ margin: '0 0 20px 0', fontSize: '18px', color: '#444' }}>Popularnost Zrva (kg)</h2>
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                            {data.popularnijaZrna.map((z, index) => (
                                <div key={index}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px', fontSize: '14px' }}>
                                        <span>{z.naziv}</span>
                                        <strong>{z.ukupnaKolicina} kg</strong>
                                    </div>
                                    <div style={{ background: '#e9ecef', borderRadius: '4px', height: '12px', width: '100%' }}>
                                        <div style={{ background: '#ffc107', height: '100%', borderRadius: '4px', width: `${(z.ukupnaKolicina / maxKafa) * 100}%` }}></div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </section>

                    {/* Trendovi prženja */}
                    <section style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                        <h2 style={{ margin: '0 0 20px 0', fontSize: '18px', color: '#444' }}>Trendovi Prženja (kg)</h2>
                        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                            {data.trendoviPrzenja.map((t, index) => (
                                <div key={index}>
                                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px', fontSize: '14px' }}>
                                        <span>{t.naziv} prženje</span>
                                        <strong>{t.ukupnaKolicina} kg</strong>
                                    </div>
                                    <div style={{ background: '#e9ecef', borderRadius: '4px', height: '12px', width: '100%' }}>
                                        <div style={{ background: '#6f42c1', height: '100%', borderRadius: '4px', width: `${(t.ukupnaKolicina / maxPrzenje) * 100}%` }}></div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </section>
                </div>

                {/* Opterećenost dostave po danima */}
                <section style={{ flex: 1, border: '1px solid #ddd', borderRadius: '8px', padding: '20px', background: '#fafafa' }}>
                    <h2 style={{ margin: '0 0 20px 0', fontSize: '18px', color: '#444' }}>Opterećenost Dostave (Broj dostava po danima)</h2>
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                        {data.opterecenostDostave.map((d, index) => (
                            <div key={index}>
                                <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px', fontSize: '14px' }}>
                                    <span>{new Date(d.datum).toLocaleDateString('sr-RS')}</span>
                                    <strong>{d.brojDostava} dostave/a</strong>
                                </div>
                                <div style={{ background: '#e9ecef', borderRadius: '4px', height: '12px', width: '100%' }}>
                                    <div style={{ background: '#28a745', height: '100%', borderRadius: '4px', width: `${(d.brojDostava / maxDostava) * 100}%` }}></div>
                                </div>
                            </div>
                        ))}
                    </div>
                </section>
            </div>
        </div>
    );
};