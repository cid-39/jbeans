import React from 'react';
import { useBasket } from '../../context/BasketContext';
import './Basket.css';

export const Basket: React.FC = () => {
    const {
        basket,
        updateQuantity,
        removeFromBasket
    } = useBasket();

    const handleCheckoutGateway = () => {
        window.location.href = '/checkout';
    };

    if (basket.length === 0) {
        return (
            <div className="container basket-layout">
                <h1 className="basket-title">Vaša Korpa</h1>
                <div className="empty-basket-msg">
                    <h2 style={{ color: 'var(--color-text-muted)', marginBottom: '15px' }}>Korpa je trenutno prazna.</h2>
                    <button onClick={() => window.location.href = '/'}>Povratak na prodavnicu</button>
                </div>
            </div>
        );
    }

    // Računanje ukupne cene cele korpe na osnovu dinamičkih cena iz baze
    const ukupnoZaUplatu = basket.reduce((sum, item) => {
        const cenaPoMeri = item.proizvod.zrna?.cenaPoMeri || (item.proizvod as any).cenaPoMeri || 0;
        return sum + (item.kolicina * cenaPoMeri);
    }, 0);

    return (
        <div className="container basket-layout">
            <h1 className="basket-title">Vaša Korpa</h1>

            <div className="basket-table-container">
                <table className="basket-table">
                    <thead>
                        <tr>
                            <th>Proizvod</th>
                            <th>Cena po kg</th>
                            <th>Količina</th>
                            <th>Ukupno</th>
                            <th>Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        {basket.map((item) => {
                            // Izvlačimo dinamičku cenu (iz zrna ili direktno sa proizvoda)
                            const cenaPoMeri = item.proizvod.zrna?.cenaPoMeri || (item.proizvod as any).cenaPoMeri || 0;
                            const maksimalnaKolicina = item.proizvod.zrna?.kolicinaNaStanju || item.proizvod.kolicinaPrzena || 999;

                            return (
                                <tr key={`${item.proizvod.id}-${item.proizvod.tipPrzenja.id}`}>
                                    <td>
                                        <strong>{item.proizvod.naziv}</strong>
                                        <span className="item-roast-info" style={{ display: 'block', fontSize: '13px', color: '#bc6c25', marginTop: '4px' }}>
                                            {item.proizvod.tipPrzenja.naziv} prženje
                                        </span>
                                    </td>
                                    <td>{cenaPoMeri.toLocaleString('sr-RS')} RSD</td>
                                    <td>
                                        <div className="qty-controls">
                                            {/* Smanjivanje za 0.25 */}
                                            <button
                                                className="qty-btn"
                                                disabled={item.kolicina <= 0.25}
                                                onClick={() => {
                                                    const novaKolicina = parseFloat((item.kolicina - 0.25).toFixed(2));
                                                    if (novaKolicina >= 0.25) {
                                                        updateQuantity(item.proizvod.id, novaKolicina);
                                                    }
                                                }}
                                            >
                                                -
                                            </button>
                                            <span style={{ fontWeight: 'bold', minWidth: '65px', textAlign: 'center' }}>
                                                {item.kolicina.toFixed(2)} kg
                                            </span>
                                            {/* Povećavanje za 0.25 uz proveru stanja */}
                                            <button
                                                className="qty-btn"
                                                disabled={item.kolicina + 0.25 > maksimalnaKolicina}
                                                onClick={() => {
                                                    const novaKolicina = parseFloat((item.kolicina + 0.25).toFixed(2));
                                                    if (novaKolicina <= maksimalnaKolicina) {
                                                        updateQuantity(item.proizvod.id, novaKolicina);
                                                    }
                                                }}
                                            >
                                                +
                                            </button>
                                        </div>
                                    </td>
                                    <td style={{ fontWeight: 'bold' }}>
                                        {(item.kolicina * cenaPoMeri).toLocaleString('sr-RS')} RSD
                                    </td>
                                    <td>
                                        <button
                                            className="remove-btn"
                                            onClick={() => removeFromBasket(item.proizvod.id)}
                                        >
                                            Ukloni
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>

            <div className="basket-summary-box">
                <div>
                    <span className="total-price-label">
                        Ukupno za uplatu: {ukupnoZaUplatu.toLocaleString('sr-RS')} RSD
                    </span>
                </div>
                <button className="checkout-gateway-btn" onClick={handleCheckoutGateway}>
                    Nastavi na plaćanje →
                </button>
            </div>
        </div>
    );
};