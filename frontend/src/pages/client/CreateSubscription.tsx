import React, { useEffect, useState } from 'react';
import apiClient from '../../api/apiClient';
import { productService } from '../../api/productService';
import { useAuth } from '../../context/AuthContext';
import { SirovaZrna } from '../../types';
import './Subscriptions.css';

interface TipPrzenja {
    id: number;
    naziv: string;
}

const ROAST_DESCRIPTIONS: Record<number, string> = {
    1: "Svetlo prženje zadržava originalna svojstva zrna. Odlikuje se izraženom kiselošću, cvetnim i voćnim aromama, idealno za ljubitelje filter kafe i nežnijih ukusa.",
    2: "Srednje prženje donosi savršen balans između kiselosti i gorčine. Razvijaju se note čokolade, lešnika i karamele. Najpopularniji izbor za tradicionalni espresso.",
    3: "Tamno prženje karakteriše puno telo, niska kiselost i bogat, gorak ukus sa dimljenim tonovima i dugom završnicom. Odlično za jake, intenzivne napitke."
};
const ROASTS: TipPrzenja[] = [
    { id: 1, naziv: "Svetlo" },
    { id: 2, naziv: "Srednje" },
    { id: 3, naziv: "Tamno" }
];

export const CreateSubscription: React.FC = () => {
    const { user } = useAuth();

    // Koristimo tvoj tačan tip SirovaZrna za stanje kafe
    const [kafe, setKafe] = useState<SirovaZrna[]>([]);
    // const [roasts, setRoasts] = useState<TipPrzenja[]>([]);

    const [sirovaZrnaId, setSirovaZrnaId] = useState<number>(0);
    const [tipPrzenjaId, setTipPrzenjaId] = useState<number>(1);
    const [kolicina, setKolicina] = useState<number>(0.25);
    const [period, setPeriod] = useState<number>(1);
    const [adresa, setAdresa] = useState<string>('');
    const [datumDostave, setDatumDostave] = useState<string>('');

    const [loading, setLoading] = useState<boolean>(false);
    const [success, setSuccess] = useState<boolean>(false);

    useEffect(() => {
        const loadProductsAndRoasts = async () => {
            try {
                // Učitavanje zrna preko tvog servisa
                const zrnaData = await productService.getAllZrna();
                setKafe(zrnaData);
                if (zrnaData.length > 0) setSirovaZrnaId(zrnaData[0].id);
            } catch (error) {
                console.error("Greška pri učitavanju podataka za pretplatu", error);
            }
        };

        loadProductsAndRoasts();

        const sutra = new Date();
        sutra.setDate(sutra.getDate() + 1);
        setDatumDostave(sutra.toISOString().split('T')[0]);
    }, [user]);

    const handleCreateSubscription = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!adresa.trim()) {
            alert('Molimo unesite adresu za dostavu.');
            return;
        }

        setLoading(true);

        const formatiranDatumSaVremenom = `${datumDostave}T10:00:00`;

        const payload = {
            period: period,
            adresa: adresa,
            datumDostave: formatiranDatumSaVremenom,
            stavke: [
                {
                    sirovaZrnaId: sirovaZrnaId,
                    kolicina: kolicina,
                    tipPrzenja: { id: tipPrzenjaId }
                }
            ]
        };

        try {
            await apiClient.post('/pretplata/create', payload);
            setSuccess(true);
            setTimeout(() => { window.location.href = '/pretplate'; }, 2000);
        } catch (error) {
            console.error(error);
            alert('Došlo je do greške prilikom kreiranja pretplate.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container subs-layout">
            <span className="back-link" onClick={() => window.location.href = '/pretplate'} style={{ cursor: 'pointer' }}>
                ← Nazad na moje pretplate
            </span>

            <div className="checkout-section auth-form" style={{ maxWidth: '600px', margin: '20px auto 0 auto' }}>
                <h2 style={{ color: 'var(--color-primary)' }}>Kreiranje Automatske Pretplate</h2>
                <p style={{ color: 'var(--color-text-muted)', fontSize: '14px' }}>
                    Podesite redovnu isporuku omiljene kafe direktno na kućnu adresu.
                </p>

                {success && (
                    <div className="auth-error" style={{ backgroundColor: '#eef9f0', color: 'var(--color-success)', padding: '10px', borderRadius: '4px', margin: '10px 0' }}>
                        ✓ Pretplata uspešno aktivirana! Preusmeravanje...
                    </div>
                )}

                <form onSubmit={handleCreateSubscription} className="auth-form" style={{ marginTop: '15px' }}>
                    <div className="form-group">
                        <label>Izaberite Kafu</label>
                        <select className="roast-select" value={sirovaZrnaId} onChange={(e) => setSirovaZrnaId(Number(e.target.value))}>
                            {kafe.map(p => <option key={p.id} value={p.id}>{p.naziv} ({p.cenaPoMeri} RSD/kg)</option>)}
                        </select>
                    </div>

                    <div className="form-group">
                        <label>Izaberite Stepen Prženja</label>
                        <select className="roast-select" value={tipPrzenjaId} onChange={(e) => setTipPrzenjaId(Number(e.target.value))}>
                            {ROASTS.map((roast) => (
                                <option key={roast.id} value={roast.id}>
                                    {roast.naziv} prženje
                                </option>
                            ))}
                        </select>
                    </div>

                    {/* OVDE PEJSTUJ OVAJ BLOK: Dinamički opis prženja */}
                    {ROAST_DESCRIPTIONS[tipPrzenjaId] && (
                        <div style={{ padding: '12px', background: '#f9f9f9', borderRadius: '6px', borderLeft: '4px solid var(--color-primary)', marginBottom: '15px', fontSize: '14px', color: '#555', lineHeight: '1.4' }}>
                            {ROAST_DESCRIPTIONS[tipPrzenjaId]}
                        </div>
                    )}


                    <div className="form-group">
                        <label>Količina po isporuci (u kg)</label>
                        <input
                            type="number"
                            step="0.25"
                            min="0.25"
                            className="roast-select"
                            value={kolicina}
                            onChange={(e) => setKolicina(parseFloat(e.target.value) || 0.25)}
                        />
                    </div>

                    <div className="form-group">
                        <label>Učestalost Isporuke (u danima)</label>
                        <input
                            type="number"
                            step="1"
                            min="1"
                            className="roast-select"
                            value={period}
                            onChange={(e) => setPeriod(parseInt(e.target.value, 10) || 1)}
                        />
                    </div>

                    <div className="form-group">
                        <label>Adresa za dostavu</label>
                        <input
                            type="text"
                            required
                            className="roast-select"
                            value={adresa}
                            onChange={(e) => setAdresa(e.target.value)}
                            placeholder="Unesite adresu"
                        />
                    </div>

                    <div className="form-group">
                        <label>Datum prve dostave</label>
                        <input
                            type="date"
                            required
                            className="roast-select"
                            value={datumDostave}
                            onChange={(e) => setDatumDostave(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="auth-submit-btn" disabled={loading || success}>
                        {loading ? 'Aktivacija...' : 'Aktiviraj Pretplatu'}
                    </button>
                </form>
            </div>
        </div>
    );
};