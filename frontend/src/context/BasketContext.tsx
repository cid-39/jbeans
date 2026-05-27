import React, { createContext, useContext, useState, useEffect } from 'react';
import { KorpaStavka, Proizvod } from '../types';

interface BasketContextType {
    basket: KorpaStavka[];
    addToBasket: (proizvod: Proizvod, kolicina?: number) => void;
    removeFromBasket: (proizvodId: number) => void;
    updateQuantity: (proizvodId: number, kolicina: number) => void;
    clearBasket: () => void;
    getBasketItemCount: () => number;
    getBasketTotal: () => number;
}

const BasketContext = createContext<BasketContextType | undefined>(undefined);

export const BasketProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [basket, setBasket] = useState<KorpaStavka[]>([]);

    // Load cart from session storage on initial render so data persists through page refreshes
    useEffect(() => {
        const savedBasket = sessionStorage.getItem('basket');
        if (savedBasket) {
            setBasket(JSON.parse(savedBasket));
        }
    }, []);

    // Sync basket changes to storage
    const saveBasket = (newBasket: KorpaStavka[]) => {
        setBasket(newBasket);
        sessionStorage.setItem('basket', JSON.stringify(newBasket));
    };

    const addToBasket = (proizvod: Proizvod, kolicina = 1) => {
        const existingIndex = basket.findIndex(item => item.proizvod.id === proizvod.id);
        const updatedBasket = [...basket];

        if (existingIndex > -1) {
            updatedBasket[existingIndex].kolicina += kolicina;
        } else {
            updatedBasket.push({ proizvod, kolicina });
        }

        saveBasket(updatedBasket);
    };

    const removeFromBasket = (proizvodId: number) => {
        const updatedBasket = basket.filter(item => item.proizvod.id !== proizvodId);
        saveBasket(updatedBasket);
    };

    const updateQuantity = (proizvodId: number, kolicina: number) => {
        if (kolicina <= 0) {
            removeFromBasket(proizvodId);
            return;
        }
        const updatedBasket = basket.map(item =>
            item.proizvod.id === proizvodId ? { ...item, kolicina } : item
        );
        saveBasket(updatedBasket);
    };

    const clearBasket = () => {
        saveBasket([]);
    };

    // Calculates total number of coffee bags in the cart
    const getBasketItemCount = () => {
        return basket.reduce((sum, item) => sum + item.kolicina, 0);
    };

    // Calculates overall price based on raw product metrics (e.g., using item base rates)
    const getBasketTotal = () => {
        // Note: Adjust item price reference according to how prices are assigned to products
        return basket.reduce((sum, item) => sum + (item.kolicina * 1000), 0);
    };

    return (
        <BasketContext.Provider
            value={{
                basket,
                addToBasket,
                removeFromBasket,
                updateQuantity,
                clearBasket,
                getBasketItemCount,
                getBasketTotal
            }}
        >
            {children}
        </BasketContext.Provider>
    );
};

export const useBasket = () => {
    const context = useContext(BasketContext);
    if (context === undefined) {
        throw new Error('useBasket must be used within a BasketProvider');
    }
    return context;
};