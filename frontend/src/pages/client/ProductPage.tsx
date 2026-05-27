import React, { useEffect, useState } from 'react';
import { ProductCard } from '../../components/ProductCard';
import { productService } from '../../api/productService';
import { SirovaZrna } from '../../types';
import './Storefront.css';

export const ProductPage: React.FC = () => {
    const [beans, setBeans] = useState<SirovaZrna[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadProducts = async () => {
            try {
                setLoading(true);
                const data = await productService.getAllZrna();
                setBeans(data);
            } catch (err: any) {
                setError('Nije moguće učitati katalog kafe u ovom trenutku.');
            } finally {
                setLoading(false);
            }
        };

        loadProducts();
    }, []);

    if (loading) {
        return <div className="container" style={{ textAlign: 'center', padding: '50px' }}>Učitavanje kafe iz pržionice...</div>;
    }

    if (error) {
        return <div className="container" style={{ textAlign: 'center', color: 'red', padding: '50px' }}>{error}</div>;
    }

    return (
        <div className="container storefront-layout">
            <div className="storefront-header">
                <h1>Sveže Pržena Kafa</h1>
                <p>Direktno iz pržionice do vaših vrata. Izaberite profil koji vama najviše odgovara.</p>
            </div>

            <div className="product-grid">
                {beans.map((bean) => (
                    // Privremeno prosleđujemo mapiran objekat u ProductCard 
                    // dok ne uskladimo unutrašnjost same ProductCard komponente
                    <ProductCard 
                        key={bean.id} 
                        proizvod={{
                            id: bean.id,
                            naziv: bean.naziv,
                            kolicinaPrzena: bean.kolicinaNaStanju,
                            tipPrzenja: { id: 1, naziv: "Po izboru" },
                            zrna: bean
                        }} 
                    />
                ))}
            </div>
        </div>
    );
};