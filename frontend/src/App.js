import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import CustomerForm from './component/CustomerForm';
import AdminPage from './component/AdminPage';
import CustomerPage from './component/CustomerPage';
import SalesManagerPage from './component/SalesManagerPage';
import Login from './component/Login';
import Logout from './component/Logout'; 
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/customer-form" element={<CustomerForm />} />
        <Route path="/admin/*" element={<AdminPage />} />
        <Route path="/salesmanager/*" element={<SalesManagerPage />} />
        <Route path="/customer/*" element={<CustomerPage />} />
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </div>
  );
}

// Create a Home component for the landing page
const Home = () => {
  const [backgroundImage, setBackgroundImage] = useState('https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fGJhbmtpbmclMjBiYWNrZ3JvdW5kfGVufDB8fDB8fHww');

  const homeBackgroundStyle = {
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    backgroundRepeat: 'no-repeat',
    height: '100vh',
    width: '100%',
  };

  const navbarStyle = {
    backgroundColor: '#f0f4f8', // White navbar
  };

  const navLinkStyle = {
    color: '#000080', // Dark text for contrast
    fontWeight: 'bold',
  };

  const sectionStyle = {
    backgroundColor: '#f0f4f8', 
    color: '#000080', 
    padding: '20px',
    display: 'flex',
    justifyContent: 'space-around',
  };

  return (
    <div style={homeBackgroundStyle}>
      <nav className="navbar navbar-expand-lg navbar-light fixed-top" style={navbarStyle}>
        <div className="container-fluid">
          <a className="navbar-brand" href="/" style={navLinkStyle}>CCAS</a>
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
          <div className="collapse navbar-collapse">
            <ul className="navbar-nav me-auto">
              <li className="nav-item">
                <a className="nav-link" href="/" style={navLinkStyle}>Home</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#about-us" style={navLinkStyle}>About Us</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#about-us" style={navLinkStyle}>Contact Us</a>
              </li>
            </ul>
            <ul className="navbar-nav ms-auto">
              <li className="nav-item">
                <a className="nav-link" href="/login" style={navLinkStyle}>Login</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="/customer-form" style={navLinkStyle}>Register</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>

      <div className="d-flex justify-content-center align-items-center min-vh-100">
        <div>
          <h2 style={{color :'#f0f4f8'}}>Welcome to the Credit Card Approval App</h2>
        </div>
      </div>

      <div id="about-us" style={sectionStyle}>
        <div>
          <h3>About Us</h3>
          <p>We are dedicated to providing the best credit card services.</p>
          <p>Customer satisfaction is our first proiority</p>
        </div>
        <div>
          <h3>Contact Us</h3>
          <p>Email: supportccas@creditcardapp.com</p>
          <p>Phone: +123 456 7890</p>
        </div>
      </div>
      <div style={{ ...sectionStyle, borderTop: '1px solid ', marginTop: 'px', paddingTop: '5px' }}>
        <p>© 2024 CCAS . All Rights Reserved.</p>
      </div>
    </div>
  );
};

export default App;
