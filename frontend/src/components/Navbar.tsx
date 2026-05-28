import React from 'react';
import { useAuth } from '../context/AuthContext';
import { useBasket } from '../context/BasketContext';
import './Navbar.css';

export const Navbar: React.FC = () => {
    const { user, isAuthenticated, isAdmin, logout } = useAuth();
    const { getBasketItemCount } = useBasket();

    const handleNavigation = (path: string) => {
        window.location.href = path;
    };

    return (
        <nav className="navbar-header">
            <div className="navbar-container">
                {/* Clickable Brand Title */}
                <div className="nav-logo" onClick={() => handleNavigation(isAdmin ? '/admin' : '/')}>
                    ☕ JBeans Roastery
                </div>

                <ul className="nav-links">
                    {/* 1. ADMIN NAVIGATION */}
                    {isAuthenticated && isAdmin && (
                        <>
                            <li className="nav-link" onClick={() => handleNavigation('/admin/magacin')}>Magacin & Dobavljači</li>
                            <li className="nav-link" onClick={() => handleNavigation('/admin/katalog')}>Katalog Proizvoda</li>
                            <li className="nav-link" onClick={() => handleNavigation('/admin/porudzbine')}>Narudžbine & Pretplate</li>
                            <li className="nav-link" onClick={() => handleNavigation('/admin/dostave')}>Dostave</li>
                            <li className="nav-link" onClick={() => handleNavigation('/admin/analitika')}>Analitika</li>
                        </>
                    )}

                    {/* 2. CUSTOMER (KORISNIK) NAVIGATION */}
                    {isAuthenticated && !isAdmin && (
                        <>
                            <li className="nav-link" onClick={() => handleNavigation('/')}>Katalog</li>
                            <li className="nav-link" onClick={() => handleNavigation('/porudzbine')}>Moje Porudžbine</li>
                            <li className="nav-link" onClick={() => handleNavigation('/pretplate')}>Moje Pretplate</li>
                            <li className="nav-link basket-link-container" onClick={() => handleNavigation('/korpa')}>
                                Korpa
                                {getBasketItemCount() > 0 && (
                                    <span className="basket-badge">{getBasketItemCount()}</span>
                                )}
                            </li>
                        </>
                    )}

                    {/* 3. GUEST NAVIGATION */}
                    {!isAuthenticated && (
                        <>
                            <li className="nav-link" onClick={() => handleNavigation('/login')}>Prijava</li>
                            <li className="nav-link" onClick={() => handleNavigation('/register')}>Registracija</li>
                        </>
                    )}

                    {/* SHARED ACCOUNT ACTIONS */}
                    {isAuthenticated && (
                        <>
                            {/* Fixed key property name to use the updated user.username key */}
                            <span className="nav-user-info">
                                👤 {user?.username}
                            </span>
                            <li>
                                <button className="logout-btn" onClick={logout}>Odjavi se</button>
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
};