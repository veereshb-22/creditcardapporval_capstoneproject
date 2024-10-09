import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // Clear user email from local storage
    localStorage.removeItem('userEmail');
    // Redirect to home page
    navigate('/');
  }, [navigate]);

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <h2>Logging out...</h2>
    </div>
  );
};

export default Logout;
