import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export const ShowallSalesManager = () => {
    const [salesManagers, setSalesManagers] = useState([]);
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); // 'success' or 'error'

    useEffect(() => {
        fetchSalesManagers();
    }, []);

    const fetchSalesManagers = async () => {
        try {
            const response = await axios.get('http://localhost:9867/userdetails/sales-managers');
            setSalesManagers(response.data);
            console.log("Fetched Sales Managers:", response.data);
        } catch (error) {
            console.error("Error fetching sales managers:", error);
            setMessage("Error fetching sales managers.");
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
                fetchSalesManagers(); // Refresh the list
            //      setMessage(`User status changed to ${status}.`);
                setMessageType('success');
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
            <h2 className="text-center" style={{color: 'rgba(255, 255, 255, 0.8)'}}>Sales Managers Details</h2>

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
                    {salesManagers.map(manager => (
                        <tr key={manager.userTypeID}>
                            <td>{manager.userTypeID}</td>
                            <td>{manager.userName}</td>
                            <td>{manager.userDob}</td>
                            <td>{manager.userPhonenumber}</td>
                            <td>{manager.user?.userEmail}</td>
                            <td>{manager.userStatus}</td>
                            <td>
                                <button
                                    className="btn btn-success btn-sm me-2"
                                    onClick={() => changeStatus(manager.userTypeID, 'Active')}
                                    disabled={manager.userStatus === 'Active'}
                                >
                                    Activate
                                </button>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => changeStatus(manager.userTypeID, 'InActive')}
                                    disabled={manager.userStatus === 'InActive'}
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
