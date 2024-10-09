import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';

export const CustomerCardApply = () => {
    const [cards, setCards] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCards = async () => {
            try {
                const response = await axios.get('http://localhost:9867/carddetails/getactivecards');
                setCards(response.data);
            } catch (error) {
                console.error('Error fetching card details:', error);
            }
        };

        fetchCards();
    }, []);

    const handleApplyClick = (cardType) => {
        localStorage.setItem('CustomerCardTypeOption', cardType);
        navigate('/customer/applictionform'); // Updated to the new path
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4" style={{color: 'rgba(255, 255, 255, 0.8)'}}>Available Cards</h2>
            <div className="row justify-content-center"> {/* Center the cards */}
                {cards.map(card => (
                    <div className="col-md-4 mb-4" key={card.cardType}>
                        <div className="card shadow-sm"style={{ width: '350px', height: '440px', backgroundColor: '#ADD8E6' }}>
                            <img 
                                src={`http://localhost:9867${card.cardImage}`} 
                                alt={card.cardType} 
                                style={{ height: '200px', objectFit: 'cover' }} 
                            />
                            <div className="card-body text-center" style={{ backgroundColor: 'rgba(255, 255, 255, 0.8)', color :'#000080'}}> {/* Center-align card body content */}
                                <h5 className="card-title" style={{ fontSize: '1.5rem' }}>{card.cardType}</h5>
                                
                                <p className="card-text" style={{ fontSize: '1rem'}}>{card.cardDescription}</p>
                                <button 
                                    className="btn btn-primary"
                                    style={{ padding: '10px 20px', fontSize: '1rem' }}
                                    onClick={() => handleApplyClick(card.cardType)}
                                >
                                    Apply Now
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};
