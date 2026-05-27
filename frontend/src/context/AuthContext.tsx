import React, { createContext, useContext, useState, useEffect } from 'react';
import { Korisnik } from '../types'; // Menjamo u tvoj stvarni tip sa projektora

interface AuthContextType {
    user: Korisnik | null;
    token: string | null;
    isAuthenticated: boolean;
    isAdmin: boolean;
    login: (token: string, user: Korisnik) => void;
    logout: () => void;
    loading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<Korisnik | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    // Checks localStorage on load to see if a session already exists
    useEffect(() => {
        const savedToken = localStorage.getItem('token');
        const savedUser = localStorage.getItem('user');

        if (savedToken && savedUser) {
            setToken(savedToken);
            setUser(JSON.parse(savedUser));
        }
        setLoading(false);
    }, []);

    const login = (newToken: string, newUser: Korisnik) => {
        setToken(newToken);
        setUser(newUser);
        localStorage.setItem('token', newToken);
        localStorage.setItem('user', JSON.stringify(newUser));
    };

    const logout = () => {
        setToken(null);
        setUser(null);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.href = '/login';
    };

    const isAuthenticated = !!token;

    // Aligned perfectly with your new 'ADMIN' role string
    const isAdmin = user?.uloga === 'ADMIN';

    return (
        <AuthContext.Provider value={{ user, token, isAuthenticated, isAdmin, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};