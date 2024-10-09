import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';

const styles = {
    container: { padding: '20px' },
    card: {
        width: '300px',
        maxWidth: '100%',
        border: 'none',
        borderRadius: '10px',  // Slightly more rounded corners
        boxShadow: '0 6px 20px rgba(0, 0, 0, 0.2)', // Deeper shadow
        marginBottom: '20px',
        overflow: 'hidden',
         color :'#000080',
       backgroundColor: 'rgba(255, 255, 255, 0.8)', // White background
    },
    cardBody: {
        padding: '15px',
        textAlign: 'center', // Center the text
        color: '#333', // Darker text color for better readability
    },
    button: {
        padding: '10px 15px',
        borderRadius: '5px',
        border: 'none',
        cursor: 'pointer',
        margin: '5px',
    },
    primaryButton: {
        backgroundColor: '#007bff',
        color: 'white',
    },
    successButton: {
        backgroundColor: '#28a745',
        color: 'white',
    },
    dangerButton: {
        backgroundColor: '#dc3545',
        color: 'white',
    },
    input: {
        width: '100%',
        padding: '10px',
        margin: '5px 0',
        border: '1px solid #ccc',
        borderRadius: '4px',
    },
    imgContainer: {
        display: 'flex',
        justifyContent: 'center',
        marginBottom: '10px',
        height: '150px',
        overflow: 'hidden',
    },
    img: {
        maxWidth: '100%',
        maxHeight: '100%',
        objectFit: 'cover',
        cursor: 'pointer',
    },
};

const isValidImageFormat = (file) => {
    const validFormats = ['image/jpeg', 'image/png'];
    return file && validFormats.includes(file.type);
};

export const CardManagement = () => {
    const [cards, setCards] = useState([]);
    const [cardType, setCardType] = useState('');
    const [cardLimit, setCardLimit] = useState(0);
    const [cardDescription, setCardDescription] = useState('');
    const [cardStatus, setCardStatus] = useState('Active');
    const [cardImage, setCardImage] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchCards();
    }, []);

    const fetchCards = async () => {
        try {
            const response = await axios.get('http://localhost:9867/carddetails/getcard');
            console.log('Fetched cards:', response.data);
            setCards(Array.isArray(response.data) ? response.data : []);
        } catch (error) {
            console.error('Error fetching cards:', error);
        }
    };

    const handleAddCard = async (e) => {
        e.preventDefault();

        if (cardImage && !isValidImageFormat(cardImage)) {
            setError('Invalid image format. Please upload a JPEG or PNG.');
            return;
        }

        if (cardLimit < 0 || cardLimit > 1000000) {
            setError('Card limit must be between 0 and 10000.');
            return;
        }

        if (cardType.length < 5 || cardType.length > 100) {
            setError('Card type must be between 5 and 100 characters.');
            return;
        }

        if (cardDescription.length < 5 || cardDescription.length > 50) {
            setError('Card description must be between 5 and 50 characters.');
            return;
        }

        const formData = new FormData();
        formData.append('cardType', cardType);
        formData.append('cardLimit', cardLimit);
        formData.append('cardDescription', cardDescription);
        formData.append('cardStatus', cardStatus);
        formData.append('cardImage', cardImage);

        try {
            await axios.post('http://localhost:9867/carddetails/addcard', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            fetchCards();
            clearForm();
            setShowForm(false);
            setError('');
        } catch (error) {
            console.error('Error adding card:', error);
        }
    };

    const handleChangeStatus = async (card) => {
        const newStatus = card.cardStatus === 'Active' ? 'Inactive' : 'Active';
        try {
            await axios.put(`http://localhost:9867/carddetails/${card.cardType}/status`, null, {
                params: { newStatus },
            });
            fetchCards();
        } catch (error) {
            console.error('Error changing card status:', error);
        }
    };

    const clearForm = () => {
        setCardType('');
        setCardLimit(0);
        setCardDescription('');
        setCardStatus('Active');
        setCardImage(null);
        setError('');
    };

    return (
        <div style={styles.container}>
            <br />
            <h2 style={{ textAlign: 'center', color: 'rgba(255, 255, 255, 0.8)' }}>Card Management</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                <button
                    style={{ ...styles.button, ...styles.primaryButton }}
                    onClick={() => setShowForm(!showForm)}
                >
                    {showForm ? 'Hide Form' : 'Add Card'}
                </button>
            </div>
            {showForm && (
                <form onSubmit={handleAddCard} className="add-card-form mb-4" style={{backgroundColor: 'rgba(255, 255, 255, 0.8)',textAlign :'center'}} >
                    <br></br>
                    <div>
                        <label>Card Type</label>
                        <input
                            type="text"
                            style={styles.input}
                            placeholder="Card Type"
                            value={cardType}
                            onChange={(e) => setCardType(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Card Limit</label>
                        <input
                            type="number"
                            style={styles.input}
                            placeholder="Card Limit"
                            value={cardLimit}
                            onChange={(e) => setCardLimit(Number(e.target.value))}
                            required
                        />
                    </div>
                    <div>
                        <label>Card Description</label>
                        <input
                            type="text"
                            style={styles.input}
                            placeholder="Card Description"
                            value={cardDescription}
                            onChange={(e) => setCardDescription(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Card Image</label>
                        <input
                            type="file"
                            style={styles.input}
                            accept="image/jpeg,image/png"
                            onChange={(e) => setCardImage(e.target.files[0])}
                            required
                        />
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                        <button type="submit" style={{ ...styles.button, ...styles.successButton }}>
                            Submit
                        </button>
                    </div>
                </form>
            )}
            <h2 style={{ textAlign: 'center', color: 'rgba(255, 255, 255, 0.8)' }}>Card List</h2>
            <div style={styles.container}>
                <div className="row">
                    {Array.isArray(cards) && cards.length > 0 ? (
                        cards.map((card) => (
                            <div key={card.cardType} className="col-md-4 mb-3">
                                <div style={styles.card}>
                                    <div style={styles.cardBody}>
                                        <div style={styles.imgContainer}>
                                            {card.cardImage && (
                                                <img
                                                    src={`http://localhost:9867${card.cardImage}`}
                                                    alt={card.cardType}
                                                    style={styles.img}
                                                />
                                            )}
                                        </div>
                                        <div className="text-center">
                                            <h4>{card.cardType}</h4>
                                            <p>Limit: Rs.{card.cardLimit}</p>
                                            <p>Description: {card.cardDescription}</p>
                                            <p>Status: {card.cardStatus}</p>
                                            <button
                                                onClick={(e) => {
                                                    e.stopPropagation();
                                                    handleChangeStatus(card);
                                                }}
                                                className={`btn ${card.cardStatus === 'Active' ? 'btn-danger' : 'btn-success'}`}
                                            >
                                                {card.cardStatus === 'Active' ? 'Deactivate' : 'Activate'}
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        <p>No cards found.</p>
                    )}
                </div>
            </div>
        </div>
    );
};
