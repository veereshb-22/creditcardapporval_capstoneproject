import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export const ShowallCustomer = () => {
    const [customers, setCustomers] = useState([]);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); // 'success' or 'error'

    useEffect(() => {
        fetchCustomers();
    }, []);

    const fetchCustomers = async () => {
        try {
            const response = await axios.get('http://localhost:9867/userdetails/customers');
            setCustomers(response.data);
        } catch (error) {
            console.error("Error fetching customers:", error);
            setMessage("Error fetching customers.");
            setMessageType('error');
        }
    };

    const changeStatus = async (userTypeID, status) => {
        const action = status === 'Active' ? 'Activate' : 'Deactivate';
        const confirmed = window.confirm(`Are you sure you want to ${action} this user?`);
        if (confirmed) {
            try {
                await axios.put(`http://localhost:9867/userdetails/${userTypeID}/status`, null, {
                    params: { status }
                });
               
                setMessageType('success');
                fetchCustomers(); // Refresh the list
            } catch (error) {
                console.error("Error changing user status:", error.response ? error.response.data : error.message);
                setMessage("Error changing user status.");
                setMessageType('error');
            }
        }
    };

    return (
        <div className="container mt-4">
            <br></br>
            <br></br>
            <h2 className="text-center" style={{color: 'rgba(255, 255, 255, 0.8)'}}>Customer Details</h2>

            {message && (
                <div className={`alert alert-${messageType === 'success' ? 'success' : 'danger'}`} role="alert">
                    {message}
                </div>
            )}

            <table className="table table-striped table-bordered table-light">
                <thead className="table-primary">
                    <tr>
                        <th>User Type ID</th>
                        <th>User Name</th>
                        <th>Date of Birth</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {customers.map(customer => (
                        <tr key={customer.userTypeID}>
                            <td>{customer.userTypeID}</td>
                            <td>{customer.userName}</td>
                            <td>{customer.userDob}</td>
                            <td>{customer.userPhonenumber}</td>
                            <td>{customer.user?.userEmail}</td>
                            <td>{customer.userStatus}</td>
                            <td>
                                <button
                                    className="btn btn-success btn-sm me-2"
                                    onClick={() => changeStatus(customer.userTypeID, 'Active')}
                                    disabled={customer.userStatus === 'Active'}
                                >
                                    Activate
                                </button>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => changeStatus(customer.userTypeID, 'InActive')}
                                    disabled={customer.userStatus === 'InActive'}
                                >
                                    Deactivate
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};
