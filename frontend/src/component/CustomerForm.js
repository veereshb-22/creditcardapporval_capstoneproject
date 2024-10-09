import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { HomeNavBar } from './HomeNavBar';
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const CustomerForm = () => {
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

    const validationSchema = Yup.object().shape({
        userName: Yup.string()
            .min(3, 'User name must be at least 3 characters')
            .max(30, 'User name can’t be more than 30 characters')
            .required('User name is required'),
        userEmail: Yup.string()
            .email('Invalid email format')
            .max(30, 'Email length can’t be more than 30 characters')
            .matches(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com)$/, 'Please enter a valid email')
            .test('only-one-com', 'Email should contain only one .com', (value) => {
                return value ? (value.match(/\.com/g) || []).length === 1 : true;
            })
            .required('Email is required'),
        userPassword: Yup.string()
            .min(8, 'Password must be at least 8 characters')
            .max(20, 'Password can’t be more than 20 characters')
            .matches(/[!@#$%^&*(),.?":{}|<>]/, 'Password must contain at least one special character')
            .required('Password is required'),
        userDob: Yup.date()
            .required('Date of Birth is required')
            .test('age', 'You must be at least 18 years old', (value) => {
                if (!value) return false;
                const today = new Date();
                const birthDate = new Date(value);
                const age = today.getFullYear() - birthDate.getFullYear();
                const monthDifference = today.getMonth() - birthDate.getMonth();
                return age > 18 || (age === 18 && monthDifference >= 0);
            }),
        userPhonenumber: Yup.string()
            .matches(/^[789]\d{9}$/, 'Phone number must start with 7, 8, or 9 and contain exactly 10 digits')
            .required('Number Required'),
        userAddress: Yup.string()
            .min(10, 'Address should be minimum 10 characters')
            .max(40, 'Address should be maximum 40 characters')
            .required('Address is required'),
    });

    const handleSubmit = async (values, { setSubmitting }) => {
        const formattedDob = values.userDob; // Ensure this is in YYYY-MM-DD format

        const payload = {
            ...values,
            userDob: formattedDob,
            userStatus: 'Active',
            roleID: 'R01',
        };

        console.log("Submitting user data:", payload);

        try {
            await axios.post('http://localhost:9867/userdetails/createuser', payload);
            setMessage("User created successfully.");
            setMessageType('success');

            // Redirect to another page, e.g., "/home"
            navigate('/login'); // Change '/home' to your desired route
        } catch (error) {
            if (error.response) {
                const errorMessage = error.response.data;
                if (errorMessage.includes("UserCredential already exists")) {
                    setMessage("User Credential already exists.");
                } else {
                    setMessage(errorMessage);
                }
            } else {
                setMessage('Error creating user.');
            }
            setMessageType('error');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div>
            <style>
                {`
                    .form-container {
                        background-image: url("https://wallpapercave.com/wp/wp6602995.jpg");
                        background-size: cover;
                        background-position: center;
                        min-height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: flex-start;
                        padding-top: 60px;
                    }
                    .form-style {
                        background-color: rgba(250, 250, 250, 0.8);
                        padding: 20px;
                        border-radius: 10px;
                        width: 600px;
                        margin-top: 20px;
                    }
                    .btn-primary {
                        background-color: #00008b; // Dark blue button
                        border: none;
                        color: #ffffff; 
                    }
                    .btn-primary:hover {
                        background-color: #31b0d5;
                    }
                    .form-label {
                        text-align: center;
                        display: block;
                    }
                    .form-heading {
                        text-align: center;
                        margin-bottom: 20px;
                    }
                    .error-message {
                        width: 100%;
                        margin-top: 10px;
                        color: red;
                    }
                `}
            </style>

            <div className="form-container">
                <HomeNavBar />
                <div className="form-style">
                    {message && (
                        <div className={`alert alert-${messageType === 'success' ? 'success' : 'danger'}`} role="alert">
                            {message}
                        </div>
                    )}
                    <h2 className="form-heading">Register</h2>
                    <Formik
                        initialValues={{ 
                            userName: '', 
                            userEmail: '', 
                            userPassword: '', 
                            userDob: '', 
                            userPhonenumber: '', 
                            userAddress: ''
                        }}
                        validationSchema={validationSchema}
                        onSubmit={handleSubmit}
                    >   
                        {({ isSubmitting }) => (
                            <Form>
                                <div className="mb-3">
                                    <label htmlFor="userName" className="form-label">User Name</label>
                                    <Field type="text" name="userName" className="form-control" />
                                    <ErrorMessage name="userName" component="div" className="error-message" />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="userEmail" className="form-label">Email</label>
                                    <Field type="email" name="userEmail" className="form-control" />
                                    <ErrorMessage name="userEmail" component="div" className="error-message" />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="userPassword" className="form-label">Password</label>
                                    <Field type="password" name="userPassword" className="form-control" />
                                    <ErrorMessage name="userPassword" component="div" className="error-message" />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="userDob" className="form-label">Date of Birth</label>
                                    <Field type="date" name="userDob" className="form-control" />
                                    <ErrorMessage name="userDob" component="div" className="error-message" />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="userPhonenumber" className="form-label">Phone Number</label>
                                    <div className="mb-3 input-group">
                                        <span className="input-group-text">+91</span>
                                        <Field type="text" name="userPhonenumber" className="form-control" />
                                    </div>
                                    <ErrorMessage name="userPhonenumber" component="div" className="error-message" />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="userAddress" className="form-label">Address</label>
                                    <Field type="text" name="userAddress" className="form-control" />
                                    <ErrorMessage name="userAddress" component="div" className="error-message" />
                                </div>

                                <button type="submit" className="btn btn-primary w-100" disabled={isSubmitting}>
                                    Register
                                </button>
                            </Form>
                        )}
                    </Formik>
                </div>
            </div>
        </div>
    );
};

export default CustomerForm;
