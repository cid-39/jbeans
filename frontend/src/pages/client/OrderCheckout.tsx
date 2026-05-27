import React, { useState } from 'react';
import { useBasket } from '../../context/BasketContext';
import { useAuth } from '../../context/AuthContext';
import apiClient from '../../api/apiClient';
import { orderService } from '../../api/orderService';
import './Checkout.css';

export const OrderCheckout: React.FC = () => {
    const { basket, clearBasket } = useBasket();
    const { user } = useAuth();

    // Računanje sutrašnjeg datuma u formatu YYYY-MM-DD
    const getTomorrowDateString = (): string => {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        return tomorrow.toISOString().split('T')[0];
    };

    const [adresa, setAdresa] = useState<string>('');
    const [datumDostave, setDatumDostave] = useState(getTomorrowDateString());
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);

    // 1. Izračunavanje ukupne težine kafe u korpi (sabiramo kilograme)
    const ukupnaTezina = basket.reduce((sum, item) => sum + item.kolicina, 0);

    // 2. Izračunavanje cene kafe na osnovu cenaPoMeri
    const cenaKafe = basket.reduce((sum, item) => {
        // Pokušavamo da izvučemo cenu sa svih mogućih mesta u objektu
        const cenaPoMeri = item.proizvod.zrna?.cenaPoMeri || (item.proizvod as any).cenaPoMeri || 0;
        return sum + (item.kolicina * cenaPoMeri);
    }, 0);

    // 3. Logika za dostavu: Ispod 3kg se plaća 350 RSD, preko 3kg je besplatno
    const cenaDostave = ukupnaTezina < 3 ? 350 : 0;
    const ukupnaCenaSaDostavom = cenaKafe + cenaDostave;

    const handlePlaceOrder = async (e: React.FormEvent) => {
        e.preventDefault();
        if (basket.length === 0) return;
        if (!adresa.trim()) {
            alert('Molimo vas unesite adresu za dostavu.');
            return;
        }

        setLoading(true);

        // Mapiranje stavki uz eksplicitno pretvaranje u Number
        const stavke = basket.map((item) => ({
            sirovaZrnaId: Number(item.proizvod.id),
            kolicina: Number(item.kolicina),
            tipPrzenja: {
                id: Number(item.proizvod.tipPrzenja.id)
            }
        }));

        const payload = {
            datumDostave: `${datumDostave}T14:00:00`,
            adresa: adresa.trim(),
            stavke: stavke
        };

        try {
            // Pozivamo novi servis
            await orderService.createOrder(payload);
            clearBasket();
            setSuccess(true);
        } catch (error: any) {
            // Ako nas interceptor baci na login, ovo se neće ni izvršiti
            // Ali ako dobijemo običnu grešku, ispisaće je ovdje
            alert('Došlo je do greške prilikom kreiranja porudžbine. Proverite prava pristupa.');
        } finally {
            setLoading(false);
        }
    };

    if (success) {
        return (
            <div className="container checkout-layout" style={{ textAlign: 'center' }}>
                <div className="checkout-section" style={{ maxWidth: '500px', margin: '0 auto' }}>
                    <h1 style={{ color: 'var(--color-success)', marginBottom: '15px' }}>✓ Porudžbina uspešna!</h1>
                    <p>Vaša kafa je uspešno poručena. Očekujte dostavu na adresi <strong>{adresa}</strong> dana: <strong>{datumDostave}</strong>.</p>
                    <button onClick={() => (window.location.href = '/')} style={{ marginTop: '20px' }}>
                        Nazad na prodavnicu
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="container checkout-layout">
            <h1 style={{ color: 'var(--color-primary)', marginBottom: '30px' }}>Završetak Porudžbine</h1>

            <div className="checkout-grid">
                {/* Leva strana: Forma za unos podataka */}
                <form className="checkout-section auth-form" onSubmit={handlePlaceOrder}>
                    <h2>Podaci za Dostavu</h2>

                    <div className="form-group">
                        <label>Adresa za Isporuku</label>
                        <input
                            type="text"
                            required
                            placeholder="Unesite vašu punu adresu"
                            value={adresa}
                            onChange={(e) => setAdresa(e.target.value)}
                        />
                    </div>

                    <div className="form-group">
                        <label>Datum Isporuke</label>
                        <div className="delivery-notice" style={{ fontSize: '13px', color: '#666', marginBottom: '5px' }}>
                            💡 Najraniji mogući datum isporuke je sutradan.
                        </div>
                        <input
                            type="date"
                            required
                            className="roast-select"
                            min={getTomorrowDateString()}
                            value={datumDostave}
                            onChange={(e) => setDatumDostave(e.target.value)}
                        />
                    </div>

                    {/* Napomene o plaćanju i dostavi */}
                    <div style={{ marginTop: '20px', padding: '15px', background: '#f9f9f9', borderRadius: '6px', fontSize: '14px', borderLeft: '4px solid var(--color-primary)' }}>
                        <p style={{ margin: '0 0 8px 0', fontWeight: 'bold' }}>Način plaćanja:</p>
                        <p style={{ margin: '0 0 15px 0', color: '#555' }}>Plaćanje se vrši isključivo <strong>pouzećem</strong> (gotovinom prilikom preuzimanja pošiljke).</p>

                        <p style={{ margin: '0 0 8px 0', fontWeight: 'bold' }}>Informacije o dostavi:</p>
                        <p style={{ margin: 0, color: '#555' }}>
                            Za porudžbine manje od 3 kg, cena dostave iznosi <strong>350 RSD</strong>.
                            {ukupnaTezina >= 3 ? (
                                <span style={{ color: 'green', fontWeight: 'bold', display: 'block', marginTop: '5px' }}>🎉 Vaša porudžbina qualifies za besplatnu dostavu!</span>
                            ) : (
                                <span style={{ color: '#bc6c25', display: 'block', marginTop: '5px' }}>Dodajte još {(3 - ukupnaTezina).toFixed(2)} kg kafe za besplatnu dostavu.</span>
                            )}
                        </p>
                    </div>

                    <button type="submit" className="auth-submit-btn" style={{ marginTop: '25px', width: '100%' }} disabled={loading || basket.length === 0}>
                        {loading ? 'Kreiranje porudžbine...' : 'Potvrdi i Poruči'}
                    </button>
                </form>

                {/* Desna strana: Pregled Korpe i obračun */}
                <div className="checkout-section">
                    <h2>Pregled Korpe</h2>
                    {basket.map((item) => {
                        // Sigurno izvlačenje cene za svaku pojedinačnu stavku
                        const cenaPoMeri = item.proizvod.zrna?.cenaPoMeri || (item.proizvod as any).cenaPoMeri || 0;
                        return (
                            <div className="checkout-summary-item" key={`${item.proizvod.id}-${item.proizvod.tipPrzenja.id}`} style={{ paddingBottom: '10px', marginBottom: '10px', borderBottom: '1px solid #eee' }}>
                                <div>
                                    <strong>{item.proizvod.naziv}</strong>
                                    <span style={{ display: 'block', fontSize: '13px', color: 'var(--color-text-muted)', marginTop: '3px' }}>
                                        Prženje: {item.proizvod.tipPrzenja.naziv} | Količina: {item.kolicina.toFixed(2)} kg
                                    </span>
                                </div>
                                <span style={{ fontWeight: '500' }}>
                                    {(item.kolicina * cenaPoMeri).toLocaleString('sr-RS')} RSD
                                </span>
                            </div>
                        );
                    })}

                    <div style={{ marginTop: '20px', fontSize: '15px', display: 'flex', flexDirection: 'column', gap: '8px' }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <span>Ukupna težina:</span>
                            <span style={{ fontWeight: '500' }}>{ukupnaTezina.toFixed(2)} kg</span>
                        </div>
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <span>Cena kafe:</span>
                            <span>{cenaKafe.toLocaleString('sr-RS')} RSD</span>
                        </div>
                        <div style={{ display: 'flex', justifyContent: 'space-between', color: cenaDostave === 0 ? 'green' : 'inherit' }}>
                            <span>Dostava:</span>
                            <span>{cenaDostave === 0 ? 'BESPLATNA' : `${cenaDostave} RSD`}</span>
                        </div>
                        <hr style={{ border: 'none', borderTop: '1px solid #ccc', margin: '10px 0' }} />
                        <div className="checkout-total-row" style={{ display: 'flex', justifyContent: 'space-between', fontSize: '20px', fontWeight: 'bold' }}>
                            <span>Ukupno za uplatu:</span>
                            <span style={{ color: 'var(--color-primary)' }}>{ukupnaCenaSaDostavom.toLocaleString('sr-RS')} RSD</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};