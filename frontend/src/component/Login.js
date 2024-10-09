import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import 'bootstrap/dist/css/bootstrap.min.css';
import { HomeNavBar } from './HomeNavBar';

const Login = () => {
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      email: '',
      password: '',
    },
    validationSchema: Yup.object({
      email: Yup.string()
        .email('Invalid email format')
        .max(30, 'Email must be 30 characters or less')  
        .matches(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com)$/, 'Please enter a valid email')
        .required('Required'),
      password: Yup.string()
        .min(8, 'Password must be at least 8 characters')
        .max(30, 'Password must be less than 30 characters')
        .matches(/[!@#$%^&*(),.?":{}|<>]/, 'Password must contain at least one special character')
        .required('Required'),
    }),
    onSubmit: async (values) => {
      setError(''); // Clear previous errors
      try {
        const response = await axios.post('http://localhost:9867/api/login', {
          userEmail: values.email,
          userPassword: values.password,
        });
        const user = response.data;
        if (user) {
          localStorage.setItem('userEmail', values.email);
          if (user.role.roleID === 'R01') {
            navigate('/customer');
          } else if (user.role.roleID === 'R02') {
            navigate('/salesmanager');
          } else if (user.role.roleID === 'R03') {
            navigate('/admin');
          } else {
            setError('Invalid role');
          }
        }
      } catch (error) {
        if (error.response && error.response.status === 401) {
          setError('Invalid email or password');
        } else {
          setError('An error occurred. Please try again later.');
        }
      }
    },
  });

  const backgroundStyle = {
    backgroundImage: 'url("https://wallpapercave.com/wp/wp6602995.jpg")',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    minHeight: '100vh',
    display: 'flex',
    flexDirection: 'column',
  };

  const formContainerStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
  };

  const formStyle = {
    color: '#000000', // Black text
    backgroundColor: 'rgba(255, 255, 255, 0.8)', // Light semi-transparent background
    border: 'none',
    textAlign: 'center', // Center-align text
  };

  
  const buttonStyle = {
    backgroundColor: '#00008b', // Dark blue button
    border: 'none',
    color: '#ffffff', // White text on button
  };

  const buttonHoverStyle = {
    backgroundColor: '#0000cd', // Lighter blue on hover
  };

  const errorPopupStyle = {
    position: 'fixed',
    top: '20%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    zIndex: 1000,
    backgroundColor: '#f8d7da',
    color: '#721c24',
    padding: '1rem',
    borderRadius: '5px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
  };

  return (
    <div style={backgroundStyle}>
      <HomeNavBar style={{ backgroundColor: '#ffffff' }} />
      <div style={formContainerStyle}>
        <div className="container d-flex flex-column justify-content-center">
         
          {error && <div style={errorPopupStyle} role="alert">{error}</div>}
          <form
            onSubmit={formik.handleSubmit}
            className="border rounded p-4 shadow-sm mx-auto"
            style={{ maxWidth: '900px', ...formStyle }}
          > <h2 className="text-center mb-" >Login</h2>
            <div className="mb-4">
              <label htmlFor="email" className="form-label">Email ID</label>
              <input
                type="email"
                className={`form-control ${formik.touched.email && formik.errors.email ? 'is-invalid' : ''}`}
                id="email"
                {...formik.getFieldProps('email')}
                style={{ ...formStyle,  }}
                
              />
              {formik.touched.email && formik.errors.email ? (
                <div className="invalid-feedback">{formik.errors.email}</div>
              ) : null}
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input
                type="password"
                className={`form-control ${formik.touched.password && formik.errors.password ? 'is-invalid' : ''}`}
                id="password"
                {...formik.getFieldProps('password')}
                style={{ ...formStyle,  }}
                
              />
              {formik.touched.password && formik.errors.password ? (
                <div className="invalid-feedback">{formik.errors.password}</div>
              ) : null}
            </div>
            <button
              type="submit"
              className="btn w-100"
              style={buttonStyle} 
              onMouseOver={(e) => e.target.style.backgroundColor = buttonHoverStyle.backgroundColor}
              onMouseOut={(e) => e.target.style.backgroundColor = buttonStyle.backgroundColor}
            >
              Login
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
