import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export const SalesManagerForm = () => {
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState('');

    const validationSchema = Yup.object().shape({
        userAddress: Yup.string()
            .min(10, 'Minimum address character should be 10')
            .max(40, 'Reached maximum length of address')
            .required('User address is required'),
        userName: Yup.string()
            .min(3, 'User name must be at least 3 characters')
            .max(30, 'User name can’t be more than 30 characters')
            .required('User name is required'),
        userEmail: Yup.string()
            .email('Invalid email format')
            .max(30, 'Email length can’t be more than 30 characters')
            .matches(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com)$/, 'Please enter a valid email')
            .required('Email is required'),
        userPassword: Yup.string()
            .min(8, 'Password must be at least 8 characters')
            .max(20, 'Password can’t be more than 20 characters')
            .matches(/[!@#$%^&*(),.?":{}|<>]/, 'Password must contain at least one special character')
            .required('Password is required'),
        userDob: Yup.date()
            .max(new Date(), 'Date must be in the past')
            .min(new Date(new Date().setFullYear(new Date().getFullYear() - 120)), 'Date of birth must be reasonable')
            .required('Date of birth is required'),
        userPhonenumber: Yup.string()
            .matches(/^[789]\d{9}$/, 'Phone number must start with 7, 8, or 9 and be 10 digits long')
            .required('Phone number is required'),
    });

    const handleSubmit = async (values, { setSubmitting }) => {
        const formattedDob = values.userDob;

        const payload = {
            ...values,
            userDob: formattedDob,
            userStatus: 'Active',
            roleID: 'R02',
        };

        console.log("Submitting user data:", payload);

        try {
            await axios.post('http://localhost:9867/userdetails/createuser', payload);
            setMessage("User created successfully.");
            setMessageType('success');
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
        <div className="container mt-4">
            <style>
                {`
                    .container {
                        
                        background-size: cover;
                        background-position: center;
                        min-height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: flex-start;
                        padding-top: 10px;
                    }
                     .btn-primary {
                        background-color: #00008b; // Dark blue button
                        border: none;
                        color: #ffffff; 
                    }

                    .form-style {
                        background-color: rgba(255, 255, 255, 0.9);
                        padding: 20px;
                        border-radius: 10px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                        width: 600px; /* Set a fixed width */
                    }
                    .form-label {
                        text-align: center;
                        display: block;
                    
                    .error-message {
                        width: 100%;
                        margin-top: 10px;
                        color: red;
                    }
                `}
            </style>

            <div className="form-style">
            <h3 style={{ textAlign: 'center' }}>Sales Manager Registration</h3>
                {message && (
                    <div className={`alert alert-${messageType === 'success' ? 'success' : 'danger'}`} role="alert">
                        {message}
                    </div>
                )}

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
                                <ErrorMessage name="userName" component="div" className="text-danger" />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="userEmail" className="form-label">Email</label>
                                <Field type="email" name="userEmail" className="form-control" />
                                <ErrorMessage name="userEmail" component="div" className="text-danger" />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="userPassword" className="form-label">Password</label>
                                <Field type="password" name="userPassword" className="form-control" />
                                <ErrorMessage name="userPassword" component="div" className="text-danger" />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="userDob" className="form-label">Date of Birth</label>
                                <Field type="date" name="userDob" className="form-control" />
                                <ErrorMessage name="userDob" component="div" className="text-danger" />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="userPhonenumber" className="form-label">Phone Number</label>
                                <Field type="text" name="userPhonenumber" className="form-control" />
                                <ErrorMessage name="userPhonenumber" component="div" className="text-danger" />
                            </div>

                            <div className="mb-3">
                                <label htmlFor="userAddress" className="form-label">Address</label>
                                <Field type="text" name="userAddress" className="form-control" />
                                <ErrorMessage name="userAddress" component="div" className="text-danger" />
                            </div>
                            
                            <button type="submit" className="btn btn-primary w-100" disabled={isSubmitting}>
                                Register
                            </button>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    );
};
