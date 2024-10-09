import React from 'react';
import { Link } from 'react-router-dom';

export const AdminNavBar = () => {
    const navbarStyle = {
        backgroundColor: '#f0f4f8', 
        boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)' 
    };

    const navLinkStyle = {
        color: '#000080', // Change this to your desired link color
        fontWeight: 'bold',
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light fixed-top" style={navbarStyle}>
            <div className="container-fluid">
                <Link className="navbar-brand" to="/admin" style={navLinkStyle}>CCAS</Link>
              
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
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link" to="/admin/customers" style={navLinkStyle}>Customer</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/admin/sales-managers" style={navLinkStyle}>Sales Manager</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/admin/register-sales-manager" style={navLinkStyle}>Add Sales Manager</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/admin/card-management" style={navLinkStyle}>Manage Card</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/admin/manageapplication" style={navLinkStyle}>Manage Application</Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/logout" style={navLinkStyle}>Logout</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};
