import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { CustomerCardApplication } from './CustomerCardApplicationForm';
import { CustomerApplicationStatus } from './CustomerApplicationStatus';
import { CustomerNavBar } from './CustomerNavBar';
import { CustomerCardApply } from './CustomerCardApply';
import { CustomerCreditCard } from './CustomerCreditCard';
import { CustomerMessageForm } from './CustomerMessageForm';

const CustomerPage = () => {
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
        <div style={homeBackgroundStyle}>
            <CustomerNavBar />
            <div style={contentStyle}>
                <Routes>
                    <Route path="applictionform" element={<CustomerCardApplication />} />
                    <Route path="applicationstatus" element={<CustomerApplicationStatus />} />
                    <Route path="customercardselect" element={<CustomerCardApply />} />
                    <Route path="customercreditcard" element={<CustomerCreditCard />} />
                    <Route path="customermessage" element={<CustomerMessageForm />} />
                    <Route path="/" element={
                        <div className="d-flex justify-content-center align-items-center" style={{ height: '70vh' }}>
                            <h2 style={{color :'#f0f4f8'}}>Welcome to the Customer Panel</h2>
                        </div>
                    } />
                </Routes>
            </div>
        </div>
    );
}

export default CustomerPage;
