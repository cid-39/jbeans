import React, { useState, useEffect } from 'react';
import { useBasket } from '../../context/BasketContext';
import { productService } from '../../api/productService';
import { Proizvod, TipPrzenja, SirovaZrna } from '../../types';

const ROASTS: TipPrzenja[] = [
    { id: 1, naziv: "Svetlo" },
    { id: 2, naziv: "Srednje" },
    { id: 3, naziv: "Tamno" }
];

const ROAST_DESCRIPTIONS: Record<number, string> = {
    1: "Svetlo prženje zadržava originalna svojstva zrna. Odlikuje se izraženom kiselošću, cvetnim i voćnim aromama, idealno za ljubitelje filter kafe i nežnijih ukusa.",
    2: "Srednje prženje donosi savršen balans između kiselosti i gorčine. Razvijaju se note čokolade, lešnika i karamele. Najpopularniji izbor za tradicionalni espresso.",
    3: "Tamno prženje karakteriše puno telo, niska kiselost i bogat, gorak ukus sa dimljenim tonovima i dugom završnicom. Odlično za jake, intenzivne napitke."
};

export const SingleProductPage: React.FC = () => {
    const { addToBasket } = useBasket();
    const [bean, setBean] = useState<SirovaZrna | null>(null);
    const [selectedRoastId, setSelectedRoastId] = useState<number>(ROASTS[1].id);
    const [quantity, setQuantity] = useState<number>(0.25); // Početni minimum je 0.25
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState(false);

    useEffect(() => {
        const fetchProductData = async () => {
            try {
                setLoading(true);
                const pathParts = window.location.pathname.split('/');
                const idFromUrl = Number(pathParts[pathParts.length - 1]);

                if (isNaN(idFromUrl)) {
                    setError('Nevažeći identifikator proizvoda.');
                    return;
                }

                const allBeans = await productService.getAllZrna();
                const foundBean = allBeans.find(b => b.id === idFromUrl);

                if (!foundBean) {
                    setError('Traženi proizvod nije pronađen u katalogu.');
                } else {
                    setBean(foundBean);
                }
            } catch (err: any) {
                setError('Greška pri učitavanju podataka o kafi.');
            } finally {
                setLoading(false);
            }
        };

        fetchProductData();
    }, []);

    const handleIncrement = () => {
        if (bean && quantity + 0.25 <= bean.kolicinaNaStanju) {
            // Koristimo parseFloat da izbegnemo JavaScript decimalne greške (npr. 0.1 + 0.2 = 0.300000004)
            setQuantity(prev => parseFloat((prev + 0.25).toFixed(2)));
        }
    };

    const handleDecrement = () => {
        if (quantity > 0.25) {
            setQuantity(prev => parseFloat((prev - 0.25).toFixed(2)));
        }
    };

    const handleAddClick = () => {
        if (!bean) return;

        const chosenRoast = ROASTS.find(r => r.id === selectedRoastId) || ROASTS[1];

        const customizedProduct: Proizvod = {
            id: bean.id,
            naziv: bean.naziv,
            kolicinaPrzena: bean.kolicinaNaStanju, 
            tipPrzenja: chosenRoast,
            zrna: bean
        };

        addToBasket(customizedProduct, quantity);
        setSuccessMessage(true);
        setTimeout(() => setSuccessMessage(false), 3000);
    };

    if (loading) {
        return <div className="container" style={{ textAlign: 'center', padding: '50px' }}>Učitavanje detalja kafe...</div>;
    }

    if (error || !bean) {
        return <div className="container" style={{ textAlign: 'center', color: 'red', padding: '50px' }}>{error || 'Greška.'}</div>;
    }

    return (
        <div className="container">
            <span className="back-link" style={{ cursor: 'pointer', display: 'inline-block', marginBottom: '20px' }} onClick={() => window.location.href = '/'}>
                ← Povratak na sve proizvode
            </span>

            <div className="details-container" style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
                <h1 className="details-title" style={{ fontSize: '2.2rem', margin: 0 }}>{bean.naziv}</h1>
                
                <div style={{ padding: '15px', background: '#f9f9f9', borderRadius: '6px', borderLeft: '4px solid var(--color-primary)' }}>
                    <p style={{ fontSize: '16px', margin: 0, lineHeight: '1.5', color: '#555' }}>
                        {ROAST_DESCRIPTIONS[selectedRoastId]}
                    </p>
                </div>

                <div className="roast-selection-box" style={{ marginTop: '10px' }}>
                    <label htmlFor="roast" style={{ fontWeight: 'bold', display: 'block', marginBottom: '8px' }}>
                        Izaberite Stepen Prženja:
                    </label>
                    <select
                        id="roast"
                        className="roast-select"
                        style={{ padding: '10px', fontSize: '16px', width: '200px', borderRadius: '4px' }}
                        value={selectedRoastId}
                        onChange={(e) => setSelectedRoastId(Number(e.target.value))}
                    >
                        {ROASTS.map((roast) => (
                            <option key={roast.id} value={roast.id}>
                                {roast.naziv} prženje
                            </option>
                        ))}
                    </select>
                </div>

                <div style={{ marginTop: '20px', display: 'flex', alignItems: 'center', gap: '30px', flexWrap: 'wrap' }}>
                    
                    {/* Kontrole za Količinu u inkrementima od 0.25 */}
                    {bean.kolicinaNaStanju > 0 && (
                        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                            <span style={{ fontWeight: 'bold' }}>Količina (kg):</span>
                            <div style={{ display: 'flex', alignItems: 'center', border: '1px solid #ccc', borderRadius: '4px', overflow: 'hidden' }}>
                                <button 
                                    onClick={handleDecrement}
                                    style={{ padding: '8px 14px', background: '#eee', border: 'none', cursor: 'pointer', fontSize: '16px', fontWeight: 'bold' }}
                                >
                                    -
                                </button>
                                <span style={{ padding: '0 15px', fontSize: '16px', fontWeight: 'bold', minWidth: '55px', textAlign: 'center' }}>
                                    {quantity.toFixed(2)} kg
                                </span>
                                <button 
                                    onClick={handleIncrement}
                                    style={{ padding: '8px 14px', background: '#eee', border: 'none', cursor: 'pointer', fontSize: '16px', fontWeight: 'bold' }}
                                >
                                    +
                                </button>
                            </div>
                        </div>
                    )}

                    {/* Računanje ukupne cene sa decimalima */}
                    <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
                        <span style={{ fontSize: '24px', fontWeight: 'bold' }}>
                            {(bean.cenaPoMeri * quantity).toLocaleString('sr-RS', { minimumFractionDigits: 2, maximumFractionDigits: 2 })} RSD
                        </span>
                        <button 
                            onClick={handleAddClick} 
                            disabled={bean.kolicinaNaStanju <= 0}
                            style={{ padding: '12px 24px', fontSize: '16px', cursor: bean.kolicinaNaStanju > 0 ? 'pointer' : 'not-allowed' }}
                        >
                            {bean.kolicinaNaStanju > 0 ? 'Dodaj u korpu' : 'Rasprodato'}
                        </button>
                    </div>
                </div>

                {successMessage && (
                    <div style={{ color: 'green', fontWeight: 'bold', marginTop: '10px' }}>
                        ✓ {quantity} kg kafe "{bean.naziv}" ({ROASTS.find(r => r.id === selectedRoastId)?.naziv} prženje) uspešno dodato u korpu!
                    </div>
                )}
            </div>
        </div>
    );
};