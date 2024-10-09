import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal } from 'react-bootstrap';
import { useFormik } from 'formik';
import * as Yup from 'yup';

export const CustomerCardApplication = () => {
    const [message, setMessage] = useState('');
    const [showModal, setShowModal] = useState(false);
    const cop = localStorage.getItem('CustomerCardTypeOption');

    const formik = useFormik({
        initialValues: {
            accountNumber: '',
            ifscCode: '',
            aadhaarNumber: '',
            panNumber: '',
            document: null,
            panDocuments: null,
            monthlySalary: 0,
            gender: '',
            companyName: '',
            companyAddress: '',
            accountType: '',
            userHomeAddress: '',
            creditScore: 0,
            CustomerCardTypeOption: cop
        },
        validationSchema: Yup.object({
            userHomeAddress: Yup.string()
                .min(10, 'Must be at least 10 characters')
                .max(40, 'Max size is 40')
                .required('Required'),
            accountNumber: Yup.string()
                .min(10, 'Must be at least 10 characters')
                .max(15, 'Must be 15 characters or less')
                .required('Required'),
            ifscCode: Yup.string()
                .matches(/^[A-Z]{4}0[A-Z0-9]{6}$/, 'Invalid IFSC Code')
                .required('Required'),
            aadhaarNumber: Yup.string()
                .length(12, 'Must be exactly 12 digits')
                .matches(/^[2-9]{1}[0-9]{11}$/, 'Must be a valid Aadhaar number')
                .required('Required'),
            panNumber: Yup.string()
                .length(10, 'Must be exactly 10 characters')
                .matches(/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/, 'Must be a valid PAN number')
                .required('Required'),
            document: Yup.mixed()
                .required('A document is required')
                .test('fileSize', 'The file is too large. Maximum size is 5 MB.', 
                    value => value && value.size <= 5 * 1024 * 1024),
            panDocuments: Yup.mixed()
                .required('A PAN document is required')
                .test('fileSize', 'The file is too large. Maximum size is 5 MB.', 
                    value => value && value.size <= 5 * 1024 * 1024),
            creditScore: Yup.number()
                .required('Required')
                .max(900, 'Please enter Proper civil score')
                .min(0, 'Must be a positive number'),
            monthlySalary: Yup.number()
                .required('Required')
                .min(0, 'Must be a positive number'),
            gender: Yup.string().required('Required'),
            companyName: Yup.string().required('Required'),
            companyAddress: Yup.string().min(10, 'Address should be at least 10 characters')
                .max(40, 'Limit Exceeded').required('Required'),
            accountType: Yup.string().required('Required'),
            userHomeAddress: Yup.string().min(10, 'Address should be at least 10 characters')
                .max(40, 'Limit Exceeded').required('Required'),
        }),
        onSubmit: async (values) => {
            const formDataToSend = new FormData();
            Object.keys(values).forEach(key => {
                formDataToSend.append(key, values[key]);
            });
            const userEmail = localStorage.getItem('userEmail');
            formDataToSend.append('userEmail', userEmail);
           
            try {
                const response = await axios.post('http://localhost:9867/application/customerapplicationsubmit', formDataToSend, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                setMessage(response.data);
                setShowModal(true);
            } catch (error) {
                setMessage('Error submitting application: ' + (error.response?.data || 'Unknown error'));
                setShowModal(true);
            }
        },
    });

    const handleCloseModal = () => {
        setShowModal(false);
        formik.resetForm();
        window.location.reload();
    };

    return (
        <div className="container d-flex flex-column min-vh-100 justify-content-center">
            <style>
                {`
                    .card-background {
                        background-color: #f0f4f8; /* Light blue background */
                        border-radius: 10px; /* Rounded corners */
                        padding: 20px; /* Padding */
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow */
                    }
                    .card-header {
                        
                        color: black; /* Header text color */
                        text-align: center; /* Centered text */
                        padding: 10px; /* Padding */
                        border-radius: 10px 10px 0 0; /* Rounded top corners */
                    }
                    .form-control {
                        border-radius: 5px; /* Slightly rounded input corners */
                    }
                `}
            </style>
            <div className="card-background border rounded p-4 shadow-sm mx-auto" style={{ maxWidth: '600px' }}>
                <h2 className="card-header">Customer Card Application</h2>
                <form onSubmit={formik.handleSubmit}>
                    <fieldset className="mb-4">
                        <legend className="font-weight-bold text-center">Personal Information</legend>
                        <div className="mb-3">
                            <input
                                type="text"
                                name="accountNumber"
                                className={`form-control ${formik.touched.accountNumber && formik.errors.accountNumber ? 'is-invalid' : ''}`}
                                placeholder="Account Number"
                                {...formik.getFieldProps('accountNumber')}
                            />
                            {formik.touched.accountNumber && formik.errors.accountNumber && (
                                <div className="invalid-feedback">{formik.errors.accountNumber}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                name="ifscCode"
                                className={`form-control ${formik.touched.ifscCode && formik.errors.ifscCode ? 'is-invalid' : ''}`}
                                placeholder="IFSC Code"
                                {...formik.getFieldProps('ifscCode')}
                            />
                            {formik.touched.ifscCode && formik.errors.ifscCode && (
                                <div className="invalid-feedback">{formik.errors.ifscCode}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                name="aadhaarNumber"
                                className={`form-control ${formik.touched.aadhaarNumber && formik.errors.aadhaarNumber ? 'is-invalid' : ''}`}
                                placeholder="Aadhaar Number"
                                {...formik.getFieldProps('aadhaarNumber')}
                            />
                            {formik.touched.aadhaarNumber && formik.errors.aadhaarNumber && (
                                <div className="invalid-feedback">{formik.errors.aadhaarNumber}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                name="panNumber"
                                className={`form-control ${formik.touched.panNumber && formik.errors.panNumber ? 'is-invalid' : ''}`}
                                placeholder="PAN Number"
                                {...formik.getFieldProps('panNumber')}
                            />
                            {formik.touched.panNumber && formik.errors.panNumber && (
                                <div className="invalid-feedback">{formik.errors.panNumber}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <label>Credit Score</label>
                            <input
                                type="number"
                                name="creditScore"
                                className={`form-control ${formik.touched.creditScore && formik.errors.creditScore ? 'is-invalid' : ''}`}
                                placeholder="Credit Score"
                                {...formik.getFieldProps('creditScore')}
                            />
                            {formik.touched.creditScore && formik.errors.creditScore && (
                                <div className="invalid-feedback">{formik.errors.creditScore}</div>
                            )}
                        </div>                    

                        <div className="mb-3">
                            <select
                                name="gender"
                                className={`form-control ${formik.touched.gender && formik.errors.gender ? 'is-invalid' : ''}`}
                                {...formik.getFieldProps('gender')}
                            >
                                <option value="">Select Gender</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                            {formik.touched.gender && formik.errors.gender && (
                                <div className="invalid-feedback">{formik.errors.gender}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                name="userHomeAddress"
                                className={`form-control ${formik.touched.userHomeAddress && formik.errors.userHomeAddress ? 'is-invalid' : ''}`}
                                placeholder="Permanent Address"
                                {...formik.getFieldProps('userHomeAddress')}
                            />
                            {formik.touched.userHomeAddress && formik.errors.userHomeAddress && (
                                <div className="invalid-feedback">{formik.errors.userHomeAddress}</div>
                            )}
                        </div>
                    </fieldset>

                    <fieldset className="mb-4">
                        <legend className="font-weight-bold text-center">Employment Details</legend>
                        <div className="mb-3">
                            <input
                                type="text"
                                name="companyName"
                                className={`form-control ${formik.touched.companyName && formik.errors.companyName ? 'is-invalid' : ''}`}
                                placeholder="Company Name"
                                {...formik.getFieldProps('companyName')}
                            />
                            {formik.touched.companyName && formik.errors.companyName && (
                                <div className="invalid-feedback">{formik.errors.companyName}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                name="companyAddress"
                                className={`form-control ${formik.touched.companyAddress && formik.errors.companyAddress ? 'is-invalid' : ''}`}
                                placeholder="Company Address"
                                {...formik.getFieldProps('companyAddress')}
                            />
                            {formik.touched.companyAddress && formik.errors.companyAddress && (
                                <div className="invalid-feedback">{formik.errors.companyAddress}</div>
                            )}
                        </div>

                        <div className="mb-3">
                        <label>Monthly salary</label>
                            <input
                                type="number"
                                name="monthlySalary"
                                className={`form-control ${formik.touched.monthlySalary && formik.errors.monthlySalary ? 'is-invalid' : ''}`}
                                placeholder="Monthly Salary"
                                {...formik.getFieldProps('monthlySalary')}
                            />
                            {formik.touched.monthlySalary && formik.errors.monthlySalary && (
                                <div className="invalid-feedback">{formik.errors.monthlySalary}</div>
                            )}
                        </div>

                        <div className="mb-3">
                            <select
                                name="accountType"
                                className={`form-control ${formik.touched.accountType && formik.errors.accountType ? 'is-invalid' : ''}`}
                                {...formik.getFieldProps('accountType')}
                            >
                                <option value="">Select Account Type</option>
                                <option value="Savings">Savings</option>
                                <option value="Current">Current</option>
                                <option value="Current">Salary</option>
                            </select>
                            {formik.touched.accountType && formik.errors.accountType && (
                                <div className="invalid-feedback">{formik.errors.accountType}</div>
                            )}
                        </div>
                    </fieldset>

                    <fieldset className="mb-4">
                        <legend className="font-weight-bold text-center">Documents Upload</legend>
                        <div className="mb-3">
                        <label>Pan Card</label>
                            <input
                                type="file"
                                name="document"
                                className={`form-control ${formik.touched.document && formik.errors.document ? 'is-invalid' : ''}`}
                                onChange={(event) => {
                                    formik.setFieldValue("document", event.currentTarget.files[0]);
                                }}
                            />
                            {formik.touched.document && formik.errors.document && (
                                <div className="invalid-feedback">{formik.errors.document}</div>
                            )}
                        </div>

                        <div className="mb-3">
                        <label>Bank Details</label>
                            <input
                                type="file"
                                name="panDocuments"
                                className={`form-control ${formik.touched.panDocuments && formik.errors.panDocuments ? 'is-invalid' : ''}`}
                                onChange={(event) => {
                                    formik.setFieldValue("panDocuments", event.currentTarget.files[0]);
                                }}
                            />
                            {formik.touched.panDocuments && formik.errors.panDocuments && (
                                <div className="invalid-feedback">{formik.errors.panDocuments}</div>
                            )}
                        </div>
                    </fieldset>

                    <button type="submit" className="btn btn-dark w-100" style={{ backgroundColor: '#003366' }}>Submit Application</button>
                </form>
            </div>

            <Modal show={showModal} onHide={handleCloseModal} className="modal-dialog-centered">
                <Modal.Header closeButton className="bg-dark text-white">
                    <Modal.Title>Application Status</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>{message}</p>
                </Modal.Body>
                <Modal.Footer>
                    <button
                        className="btn btn-secondary"
                        style={{ backgroundColor: '#003366', borderColor: '#003366' }}
                        onClick={handleCloseModal}
                    >
                        Close
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};
