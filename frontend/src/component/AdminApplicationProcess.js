import React, { useEffect, useState } from 'react';
import axios from 'axios';

export const AdminApplicationProcess = () => {
    const [applications, setApplications] = useState([]);
    const [error, setError] = useState('');
    const [selectedDocument, setSelectedDocument] = useState(null);
    const [isConfirmationModalOpen, setIsConfirmationModalOpen] = useState(false);
    const [pendingAction, setPendingAction] = useState(null);
    const [showMessageModal, setShowMessageModal] = useState(false);
    const [rejectionMessage, setRejectionMessage] = useState('');

    useEffect(() => {
        fetchApplications();
    }, []);

    const fetchApplications = async () => {
        try {
            const response = await axios.get('http://localhost:9867/application/processing');
            setApplications(response.data);
        } catch (err) {
            console.error(err);
            setError('Failed to fetch applications');
        }
    };

    const handleOpenConfirmation = (applicationId, newStatus) => {
        if (newStatus === 'Rejected') {
            localStorage.setItem('applicationIDofuser', applicationId); // Store applicationId in localStorage
        }
        setPendingAction({ applicationId, newStatus });
        setIsConfirmationModalOpen(true);
    };

    const handleConfirmAction = async () => {
        const { applicationId, newStatus } = pendingAction || {};

        try {
            if (newStatus === 'Rejected') {
                setShowMessageModal(true);
            } else {
                await axios.put(`http://localhost:9867/application/doc/${applicationId}/status?status=${newStatus}`);
                fetchApplications();
            }
        } catch (err) {
            console.error(err);
            setError('Failed to update status');
        } finally {
            setIsConfirmationModalOpen(false);
            setPendingAction(null);
        }
    };

    const handleSubmitRejection = async () => {
        const applicationId = localStorage.getItem('applicationIDofuser'); // Retrieve from localStorage

        if (!applicationId) {
            setError('Application ID not found');
            return;
        }

        if (!rejectionMessage.trim()) {
            setError('Message cannot be empty');
            return;
        }

        const messageForm = {
            messageInfo: rejectionMessage,
            applicationForm: { cardRequestID: applicationId },
        };

        try {
            await axios.post('http://localhost:9867/messages/postmessage', messageForm);
            await axios.put(`http://localhost:9867/application/doc/${applicationId}/status?status=Rejected`);
            fetchApplications();
            setRejectionMessage('');
            setShowMessageModal(false);
            localStorage.removeItem('applicationIDofuser'); // Clear localStorage after operation
        } catch (err) {
            console.error(err);
            setError('Failed to save rejection message');
        }
    };

    const handleDocumentToggle = (documentUrl) => {
        setSelectedDocument(selectedDocument === documentUrl ? null : documentUrl);
    };

    const styles = {
        container: { padding: '20px' },
        card: { border: 'none', borderRadius: '8px', boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)', marginBottom: '20px' },
        imgContainer: { display: 'flex', justifyContent: 'space-between', cursor: 'pointer' ,backgroundColor: 'rgba(173, 216, 230, 0.9)'},
        img: { width: '48%', height: 'auto', transition: 'transform 0.3s ease' },
        modal: { position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.7)', display: 'flex', alignItems: 'center', justifyContent: 'center' },
        modalContent: { backgroundColor: 'white', padding: '20px', borderRadius: '8px', position: 'relative', maxWidth: '80%', maxHeight: '80%', overflow: 'auto' },
        enlargedImg: { width: '100%', height: 'auto' },
        close: { position: 'absolute', top: '10px', right: '20px', cursor: 'pointer', fontSize: '24px' },
        buttonGroup: { marginTop: '10px' },
    };

    return (
        <div style={styles.container}>
            
            <br></br>
            <h2 style={{ textAlign: 'center'}}>Processing Applications</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <div className="row" >
                {applications.map(application => (
                    <div key={application.cardRequestID} className="col-md-6 mb-4">
                        <div style={styles.card}>
                            <div style={styles.imgContainer}>
                                <img 
                                    src={`http://localhost:9867${application.documentDetails}`} 
                                    alt="Document" 
                                    style={styles.img} 
                                    onClick={() => handleDocumentToggle(`http://localhost:9867${application.documentDetails}`)} 
                                />
                                {application.panDocuments && (
                                    <img 
                                        src={`http://localhost:9867${application.panDocuments}`} 
                                        alt="PAN Document" 
                                        style={styles.img} 
                                        onClick={() => handleDocumentToggle(`http://localhost:9867${application.panDocuments}`)} 
                                    />
                                )}
                            </div>
                            <div className="text-center" style={{backgroundColor: 'rgba(173, 216, 230, 0.9)'}}>
                                <h3>{application.cardRequestID}</h3>
                                <p>Account Number: {application.accountNumber}</p>
                                <p>Credit Score:{application.creditScore}</p>
                                <p>Status: {application.applicationStatus}</p>
                                <p>Card Type: {application.cardDetails.cardType }</p>
                                <div style={styles.buttonGroup}>
                                    <button 
                                        onClick={() => handleOpenConfirmation(application.cardRequestID, 'Approved')} 
                                        className="btn btn-success btn-sm mr-2">
                                        Approve
                                    </button>
                                    &nbsp;&nbsp;&nbsp;
                                    <button 
                                        onClick={() => handleOpenConfirmation(application.cardRequestID, 'Rejected')} 
                                        className="btn btn-danger btn-sm">
                                        Reject
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            {selectedDocument && (
                <div style={styles.modal}>
                    <div style={styles.modalContent}>
                        <span onClick={() => setSelectedDocument(null)} style={styles.close}>×</span>
                        <h3>Document</h3>
                        <img src={selectedDocument} alt="Selected Document" style={styles.enlargedImg} />
                    </div>
                </div>
            )}

            {isConfirmationModalOpen && (
                <div style={styles.modal}>
                    <div style={styles.modalContent}>
                        <span onClick={() => setIsConfirmationModalOpen(false)} style={styles.close}>×</span>
                        <h3>Confirmation</h3>
                        <p>Are you sure you want to {pendingAction?.newStatus} this application?</p>
                        <div style={styles.buttonGroup}>
                            <button onClick={handleConfirmAction} className="btn btn-primary mr-2">Yes</button>
                            &nbsp;&nbsp;&nbsp;                           
                            <button onClick={() => setIsConfirmationModalOpen(false)} className="btn btn-secondary">No</button>
                        </div>
                    </div>
                </div>
            )}

            {showMessageModal && (
                <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.7)', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', position: 'relative', maxWidth: '600px', width: '90%', maxHeight: '80%', overflow: 'auto' }}>
                    <span onClick={() => setShowMessageModal(false)} style={{ position: 'absolute', top: '10px', right: '20px', cursor: 'pointer', fontSize: '24px' }}>×</span>
                    <h3>Rejection Message</h3>
                    <textarea 
                        value={rejectionMessage} 
                        onChange={(e) => setRejectionMessage(e.target.value)} 
                        rows="14" 
                        style={{ width: '100%', minHeight: '50%', resize: 'none' }} // Increased height and set no resizing
                        placeholder="Enter your message here"
                        maxLength={500} 
                    />
                    <div style={{ marginTop: '10px' }}>
                        <button onClick={handleSubmitRejection} className="btn btn-primary mr-2">Submit</button>
                        &nbsp;&nbsp;&nbsp;
                        <button onClick={() => setShowMessageModal(false)} className="btn btn-secondary">Cancel</button>
                    </div>
                </div>
                </div>
            )}
        </div>
    );
};
