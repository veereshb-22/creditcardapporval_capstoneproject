import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';

const styles = {
    container: { padding: '20px' },
    card: {
        width: '800px',  // Increase the width
        maxWidth: '100%',
        border: 'none',
        borderRadius: '10px',
        boxShadow: '0 6px 15px rgba(0, 0, 0, 0.1)',
        marginBottom: '20px',
    },
    imgContainer: {
        display: 'flex',
        justifyContent: 'center',
        height: '220px',  // Increase the height
        overflow: 'hidden',
        borderRadius: '10px 10px 0 0',
    },
    img: {
        maxWidth: '100%',
        maxHeight: '100%',
        objectFit: 'cover',
    },
    cardBody: {
        textAlign: 'center', // Center-align text
        padding: '20px', color: '#000080',
        backgroundColor: 'rgba(255, 255, 255, 0.9)', // Light background for better readability
    },
};

export const CustomerCreditCard = () => {
    const [cards, setCards] = useState([]);
    const [error, setError] = useState('');
    const userEmail = localStorage.getItem('userEmail'); // Get the user email from local storage

    useEffect(() => {
        fetchCards();
    }, [userEmail]);

    const fetchCards = async () => {
        try {
            const response = await axios.get(`http://localhost:9867/creditcard/getcustomercard?email=${userEmail}`);
            console.log('Fetched cards:', response.data);
            setCards(Array.isArray(response.data) ? response.data : []);
        } catch (error) {
            console.error('Error fetching cards:', error);
            setError('Failed to fetch cards. Please try again later.');
        }
    };

    return (
        <div style={styles.container}>
            <h2 className="text-center" style={{color: 'rgba(255, 255, 255, 0.8)'}}>Credit Cards</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <div className="row justify-content-center">
                {Array.isArray(cards) && cards.length > 0 ? (
                    cards.map((card) => (
                        <div key={card.cardNumber} className="col-md-4 mb-3">
                            <div style={styles.card}>
                                <div style={styles.imgContainer}>
                                    {card.cardDetails && card.cardDetails.cardImage ? (
                                        <img
                                            src={`http://localhost:9867${card.cardDetails.cardImage}`}
                                            alt={card.cardDetails.cardType || 'Card Image'}
                                            style={styles.img}
                                        />
                                    ) : (
                                        <div style={{color: 'rgba(255, 255, 255, 0.8)'}}>No Image Available</div>
                                    )}
                                </div>

                                <div style={styles.cardBody}>
                                    <h5 className="card-title">
                                        {card.cardDetails ? card.cardDetails.cardType : 'Unknown Card Type'}
                                    </h5>
                                    <p className="card-text"><strong>Limit:</strong> Rs.{card.cardDetails ? card.cardDetails.cardLimit : 'N/A'}</p>
                                    <p className="card-text"><strong>Description:</strong> {card.cardDetails ? card.cardDetails.cardDescription : 'N/A'}</p>
                                    <p className="card-text"><strong>Status:</strong> {card.cardStatus}</p>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No credit cards found.</p>
                )}
            </div>
        </div>
    );
};
