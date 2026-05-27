import React from 'react';
import { Proizvod } from '../types';
import { useAuth } from '../context/AuthContext'; // Uvozimo auth kontekst

interface ProductCardProps {
    proizvod: Proizvod;
}

export const ProductCard: React.FC<ProductCardProps> = ({ proizvod }) => {
    const { isAuthenticated } = useAuth(); // Provjeravamo da li je korisnik ulogovan

    const handleViewDetails = () => {
        if (!isAuthenticated) {
            // Ako je korisnik gost, šaljemo ga na prijavu
            window.location.href = '/login';
        } else {
            // Ako je ulogovan, dozvoljavamo detaljan pregled i izbor prženja
            window.location.href = `/proizvod/${proizvod.id}`;
        }
    };

    const kolicina = proizvod.zrna?.kolicinaNaStanju ?? 0;
    
    let statusZaliha = '';
    let klasaZaliha = '';

    if (kolicina === 0) {
        statusZaliha = 'Nema na zalihama';
        klasaZaliha = 'status-out-of-stock';
    } else if (kolicina < 2) {
        statusZaliha = 'Niske zalihe';
        klasaZaliha = 'status-low-stock';
    } else {
        statusZaliha = 'Ima na stanju';
        klasaZaliha = 'status-in-stock';
    }

    const pravaCena = proizvod.zrna?.cenaPoMeri ?? 0;

    return (
        <div className="product-card" style={{ cursor: 'pointer', display: 'flex', flexDirection: 'column', gap: '15px' }} onClick={handleViewDetails}>
            
            {/* 1. Naslov na samom vrhu */}
            <div className="card-title-row">
                <span className="product-title" style={{ fontSize: '1.4rem', fontWeight: 'bold' }}>
                    {proizvod.naziv}
                </span>
            </div>

            {/* 2. Srednji red: Cena i zalihe zajedno */}
            <div className="card-info-row" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <span className="product-price" style={{ fontWeight: '600', fontSize: '1.1rem' }}>
                    {pravaCena.toLocaleString('sr-RS')} RSD
                </span>
                
                <span className={`stock-badge ${klasaZaliha}`} style={{ fontSize: '0.8rem', padding: '4px 10px', borderRadius: '4px', fontWeight: 'bold' }}>
                    {statusZaliha}
                </span>
            </div>

            {/* 3. Dugme na samom dnu */}
            <div className="card-footer" style={{ marginTop: 'auto', paddingTop: '5px' }}>
                <button
                    className="add-to-cart-btn"
                    disabled={kolicina === 0}
                    style={{ width: '100%' }}
                    onClick={(e) => {
                        e.stopPropagation(); // Sprečava duplo okidanje jer kartica već ima svoj onClick
                        handleViewDetails();
                    }}
                >
                    {kolicina > 0 ? 'Izaberi prženje' : 'Rasprodato'}
                </button>
            </div>
            
        </div>
    );
};