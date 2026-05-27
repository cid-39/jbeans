import React from 'react';
import { useAuth } from '../context/AuthContext';

interface GuardProps {
    children: React.ReactNode;
}

/**
 * Secures client-facing pages. 
 * Redirects unauthenticated guests to the login page.
 */
export const ProtectedRoute: React.FC<GuardProps> = ({ children }) => {
    const { isAuthenticated, loading } = useAuth();

    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '50px' }}>
                Učitavanje...
            </div>
        );
    }

    if (!isAuthenticated) {
        // Standard secure fallback
        window.location.href = '/login';
        return null;
    }

    return <>{children}</>;
};

/**
 * Secures administrative tools.
 * Blocks both unauthenticated guests and standard 'KORISNIK' accounts.
 */
export const AdminRoute: React.FC<GuardProps> = ({ children }) => {
    const { isAuthenticated, isAdmin, loading } = useAuth();

    if (loading) {
        return (
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '50px' }}>
                Učitavanje...
            </div>
        );
    }

    // If not logged in at all, kick out to login
    if (!isAuthenticated) {
        window.location.href = '/login';
        return null;
    }

    // If logged in but is a regular customer (KORISNIK), block access
    if (!isAdmin) {
        return (
            <div style={{ padding: '40px', textAlign: 'center', color: 'red' }}>
                <h2>Pristup Odbijen</h2>
                <p>Nemate administrativne privilegije za pregled ove stranice.</p>
                <button onClick={() => window.location.href = '/'}>Povratak na prodavnicu</button>
            </div>
        );
    }

    return <>{children}</>;
};