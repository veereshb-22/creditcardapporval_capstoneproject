import React from 'react';
import { Link } from 'react-router-dom';

export const HomeNavBar = () => {
    const navbarStyle = {
        backgroundColor: '#f0f4f8', // Opaque white background
        boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)', 
    };

    const navLinkStyle = {
        color: '#000080', // Change this to your desired link color
        fontWeight: 'bold',
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light fixed-top" style={navbarStyle}>
            <div className="container-fluid">
                <Link className="navbar-brand" to="/" style={navLinkStyle}>CCAS</Link>
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/" style={navLinkStyle}>Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/" style={navLinkStyle}>About Us</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/" style={navLinkStyle}>Contact Us</Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/login" style={navLinkStyle}>Login</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/customer-form" style={navLinkStyle}>Register</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};
