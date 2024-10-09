import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { ShowallCustomer } from './ShowallCustomer';
import { ShowallSalesManager } from './ShowallSalesManager';
import { SalesManagerForm } from './SalesManagerForm';
import { AdminNavBar } from './AdminNavBar';
import {CardManagement} from './CardManagement';
import { AdminApplicationProcess } from './AdminApplicationProcess';

export const AdminPage = () => {
    const homeBackgroundStyle = {
        backgroundImage: 'url("https://wallpapercave.com/wp/wp6602995.jpg")',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
    };
    const contentStyle = {
        flex: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'flex-start',
        paddingTop: '60px',
    };

    return (
        <div  style={homeBackgroundStyle}>
          
            <AdminNavBar /> {/* Render the AdminNavbar */}
            <Routes>
                <Route path="customers" element={<ShowallCustomer />} />
                <Route path="sales-managers" element={<ShowallSalesManager />} />
                <Route path="register-sales-manager" element={<SalesManagerForm />} />
                <Route path="card-management" element={<CardManagement />} />
                <Route path="/" element={
                    <div className="d-flex justify-content-center align-items-center" style={{ height: '70vh' }}>
                        <h2 style={{color :'#f0f4f8'}}>Welcome to the Admin Panel</h2>
                    </div>
                } />
                <Route path="manageapplication" element={<AdminApplicationProcess />} />
            </Routes>
        </div>
    );
};

export default AdminPage;
