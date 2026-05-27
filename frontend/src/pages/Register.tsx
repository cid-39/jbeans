import React, { useState } from 'react';
import { authService } from '../api/authService';
import './AuthPages.css';

export const Register: React.FC = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
    });
    
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState(false);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target;
        setFormData((prev) => ({ ...prev, [id]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage(null);
        setSubmitting(true);

        try {
            const message = await authService.registerClient(formData);
            setSuccessMessage(message);
        } catch (err: any) {
            setError(err.message || 'Registracija nije uspela. Proverite unete podatke.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="auth-container">
            <div className="auth-card">
                {error && <div className="auth-error">{error}</div>}

                {/* If registration is successful, hide the title and show only the centered success panel */}
                {successMessage ? (
                    <div className="auth-success-container" style={{ textAlign: 'center', padding: '20px 0' }}>
                        <div className="auth-success-message" style={{ textAlign: 'center', fontSize: '1.2rem', fontWeight: 'bold', marginBottom: '15px' }}>
                            {successMessage}
                        </div>
                        <p style={{ textAlign: 'center', marginBottom: '20px' }}>
                            Sada možete preći na stranicu za prijavu kako biste pristupili Vašem profilu.
                        </p>
                        <button 
                            className="auth-submit-btn" 
                            onClick={() => window.location.href = '/login'}
                        >
                            Idi na Prijavu
                        </button>
                    </div>
                ) : (
                    <>
                        {/* Title only shows when form is visible */}
                        <h2>Kreirajte Nalog</h2>
                        
                        <form className="auth-form" onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="username">Korisničko Ime</label>
                                <input
                                    id="username"
                                    type="text"
                                    required
                                    value={formData.username}
                                    onChange={handleInputChange}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="email">Email Adresa</label>
                                <input
                                    id="email"
                                    type="email"
                                    required
                                    value={formData.email}
                                    onChange={handleInputChange}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Lozinka</label>
                                <input
                                    id="password"
                                    type="password"
                                    required
                                    value={formData.password}
                                    onChange={handleInputChange}
                                />
                            </div>

                            <button type="submit" className="auth-submit-btn" disabled={submitting}>
                                {submitting ? 'Kreiranje naloga...' : 'Registruj se'}
                            </button>
                        </form>
                    </>
                )}

                {!successMessage && (
                    <div className="auth-switch">
                        Već imate nalog? <span onClick={() => window.location.href = '/login'}>Prijavite se ovde</span>
                    </div>
                )}
            </div>
        </div>
    );
};