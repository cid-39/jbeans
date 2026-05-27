import React, { useState } from 'react';
import { authService } from '../api/authService';
import { useAuth } from '../context/AuthContext';
import './AuthPages.css';

export const Login: React.FC = () => {
    const { login } = useAuth();
    
    // Updated state to track email, matching the backend payload key
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setSubmitting(true);

        try {
            // 1. Pass data using email and password variables matching backend JSON keys
            const userEntity = await authService.login({ email, password });
            
            // 2. Extract the token that our authService just saved to localStorage
            const savedToken = localStorage.getItem('token') || 'mock-token';

            // 3. Log the user into your global React Context engine
            login(savedToken, userEntity);

            // 4. Safely check the role string directly off the user object
            if (userEntity.uloga === 'ADMIN') {
                window.location.href = '/admin';
            } else {
                window.location.href = '/';
            }
        } catch (err: any) {
            setError(err.message || 'Pogrešno korisničko ime ili lozinka.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card">
                <h2>Prijava na Profil</h2>

                {error && <div className="auth-error">{error}</div>}

                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        {/* Changed visual labels and properties to email mapping */}
                        <label htmlFor="email">Email Adresa</label>
                        <input
                            id="email"
                            type="email"
                            required
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Lozinka</label>
                        <input
                            id="password"
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <button type="submit" className="auth-submit-btn" disabled={submitting}>
                        {submitting ? 'Prijava u toku...' : 'Prijavi se'}
                    </button>
                </form>

                <div className="auth-switch">
                    Nemate nalog? <span onClick={() => window.location.href = '/register'}>Registrujte se ovde</span>
                </div>
            </div>
        </div>
    );
};