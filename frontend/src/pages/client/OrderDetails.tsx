import React, { useEffect, useState } from 'react';
import apiClient from '../../api/apiClient';
import './Orders.css';

interface StavkaDetalj {
    id: number;
    kolicina: number;
    cena: number;
    proizvod: {
        naziv: string;
        tipPrzenja: { naziv: string };
    };
}

interface NarudzbinaDetaljno {
    id: number;
    datumKreiranja: string;
    datumIsporuke: string;
    ukupnaCena: number;
    status: 'PRIMLJENO' | 'U_PRIPREMI' | 'ISPORUCENO';
    stavke: StavkaDetalj[];
}

export const OrderDetails: React.FC = () => {
    const [order, setOrder] = useState<NarudzbinaDetaljno | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    // Simple, vanilla extraction of ID trailing parameter from window URL path matching
    const getOrderIdFromUrl = (): string => {
        const segments = window.location.pathname.split('/');
        return segments[segments.length - 1];
    };

    useEffect(() => {
        const fetchOrderDetails = async () => {
            const orderId = getOrderIdFromUrl();
            try {
                const response = await apiClient.get<NarudzbinaDetaljno>(`/narudzbina/get`, {
                    params: { id: orderId }
                });
                setOrder(response.data);
            } catch (error) {
                console.error('Greška prilikom preuzimanja detalja porudžbine.', error);
            } finally {
                setLoading(false);
            }
        };

        fetchOrderDetails();
    }, []);

    if (loading) return <div className="container orders-layout">Učitavanje specifikacije računa...</div>;
    if (!order) return <div className="container orders-layout">Porudžbina nije pronađena.</div>;

    return (
        <div className="container orders-layout">
            <span className="back-link" onClick={() => window.location.href = '/porudzbine'}>
                ← Nazad na moje porudžbine
            </span>

            <div className="invoice-card">
                <div className="invoice-header">
                    <h2>Specifikacija Porudžbine #JB-{order.id}</h2>
                    <span className={`status-badge ${order.status.toLowerCase()}`}>
                        {order.status === 'PRIMLJENO' && 'Primljeno'}
                        {order.status === 'U_PRIPREMI' && 'U pripremi'}
                        {order.status === 'ISPORUCENO' && 'Isporučeno'}
                    </span>
                </div>

                <div className="invoice-meta-grid">
                    <div>
                        <span style={{ color: 'var(--color-text-muted)', display: 'block', fontSize: '14px' }}>Datum kreiranja</span>
                        <strong>{order.datumKreiranja}</strong>
                    </div>
                    <div>
                        <span style={{ color: 'var(--color-text-muted)', display: 'block', fontSize: '14px' }}>Planirana dostava</span>
                        <strong>{order.datumIsporuke}</strong>
                    </div>
                    <div>
                        <span style={{ color: 'var(--color-text-muted)', display: 'block', fontSize: '14px' }}>Način plaćanja</span>
                        <strong>Gotovina pouzećem</strong>
                    </div>
                </div>

                <h3 style={{ color: 'var(--color-primary)', marginBottom: '15px' }}>Stavke</h3>
                <table className="basket-table">
                    <thead>
                        <tr>
                            <th>Naziv Pržene Kafe</th>
                            <th>Cena po Jedinici</th>
                            <th>Količina</th>
                            <th style={{ textAlign: 'right' }}>Ukupno</th>
                        </tr>
                    </thead>
                    <tbody>
                        {order.stavke.map((stavka) => (
                            <tr key={stavka.id}>
                                <td>
                                    <strong>{stavka.proizvod?.naziv}</strong>
                                    <span style={{ display: 'block', fontSize: '12px', color: 'var(--color-text-muted)' }}>
                                        Profil: {stavka.proizvod?.tipPrzenja?.naziv} prženje
                                    </span>
                                </td>
                                <td>{stavka.cena} RSD</td>
                                <td>{stavka.kolicina} kom</td>
                                <td style={{ textAlign: 'right', fontWeight: 'bold' }}>{stavka.kolicina * stavka.cena} RSD</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: '25px', paddingTop: '15px', borderTop: '2px solid #f3ebdf' }}>
                    <span style={{ fontSize: '22px', fontWeight: 'bold', color: 'var(--color-primary)' }}>
                        Sveukupno: {order.ukupnaCena} RSD
                    </span>
                </div>
            </div>
        </div>
    );
};