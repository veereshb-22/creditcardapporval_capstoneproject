import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaEnvelope, FaExclamationCircle } from 'react-icons/fa';
import 'bootstrap/dist/css/bootstrap.min.css';

export const CustomerMessageForm = ({ applicationId }) => {
    const [messages, setMessages] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const userEmail = localStorage.getItem('userEmail');

    useEffect(() => {
        const fetchMessages = async () => {
            try {
                const response = await axios.get(`http://localhost:9867/messages/${userEmail}`);
                setMessages(response.data);
            } catch (err) {
                setError('Failed to fetch messages');
            } finally {
                setLoading(false);
            }
        };

        fetchMessages();
    }, [userEmail]);

    useEffect(() => {
        const messageCards = document.querySelectorAll('.list-group-item');
        messageCards.forEach(card => {
            card.onmouseenter = () => {
                card.style.transform = 'scale(1.02)';
                card.style.boxShadow = '0 8px 16px rgba(0, 0, 0, 0.2)';
            };
            card.onmouseleave = () => {
                card.style.transform = 'scale(1)';
                card.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
            };
        });
    }, [messages]); // Run the hover effect whenever messages change

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Messages for Application ID: {applicationId}</h2>
            {loading && <div className="alert alert-info">Loading...</div>}
            {error && <div className="alert alert-danger">{error}</div>}
            {messages.length === 0 && !loading && (
                <div className="alert alert-warning">
                    <FaExclamationCircle className="me-2" />
                    No messages found.
                </div>
            )}
            {messages.length > 0 && (
                <div className="list-group" >
                    {messages.map((message) => (
                        <div 
                            key={message.messageID} 
                            className="list-group-item rounded mb-3" 
                            style={styles.messageCard}
                        >
                            <div className="d-flex align-items-center" >
                                <FaEnvelope className="me-2 text-primary" size={24} />
                                <div className="flex-grow-1 text-center">
                                    <h5 className="mb-1 text-dark">
                                        Message ID: {message.messageID}
                                    </h5>
                                    <p className="mb-0 text-muted">{message.messageInfo}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

// Inline styles
const styles = {
    messageCard: {
        backgroundColor: 'rgba(255, 255, 255, 0.8)', // White background for contrast
        border: '1px solid #dee2e6', // Light border
        padding: '15px',
        borderRadius: '10px', // Rounded corners
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', // Deeper shadow for a lifted effect
        transition: 'transform 0.2s, box-shadow 0.2s', // Smooth transition
    },
};
