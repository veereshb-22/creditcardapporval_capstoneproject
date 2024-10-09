import React from "react";
import { Link } from 'react-router-dom';

export const CustomerNavBar = () => {
    const navbarStyle = {
        backgroundColor: '#f0f4f8', // Opaque white background
        boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)', // Semi-transparent background
    };

    const navLinkStyle = {
        color: '#000080', // Change this to your desired link color
        fontWeight: 'bold',
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light fixed-top" style={navbarStyle}>
            <div className="container-fluid">
                <Link className="navbar-brand" to="/customer" style={navLinkStyle}>CCAS</Link>
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
                            <Link className="nav-link" to="/customer" style={navLinkStyle}>Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/customer/customercardselect" style={navLinkStyle}>Application</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/customer/applicationstatus" style={navLinkStyle}>Status</Link>
                        </li>
                    </ul>
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className="nav-link" to="/customer/customercreditcard" style={navLinkStyle}>Credit Card</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/customer/customermessage" style={navLinkStyle}>Message</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/logout" style={navLinkStyle}>Logout</Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};
