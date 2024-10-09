import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

export const CustomerApplicationStatus = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchApplications = async () => {
      const userEmail = localStorage.getItem('userEmail');
      if (!userEmail) {
        setError('User email not found in local storage.');
        setLoading(false);
        return;
      }

      try {
        const response = await axios.get(`http://localhost:9867/application/specificcustomer?email=${userEmail}`);
        setApplications(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchApplications();
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="container mt-4">
      <h2 className="text-center" style={{color: 'rgba(255, 255, 255, 0.8)'}}>Application Status</h2>
      {applications.length === 0 ? (
        <h3 style={{color :'#f0f4f8'}}>No applications found.</h3>
      ) : (
        
        <table className="table table-striped table-bordered table-light">
          <thead className="table-primary">
            <tr>
              <th>Request ID</th>
              <th>Account Number</th>
              <th>Card Type</th>
              <th>Sales Manager</th>
              <th>Monthly Salary</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {applications.map(application => (
              <tr key={application.cardRequestID}>
                <td>{application.cardRequestID}</td>
                <td>{application.accountNumber}</td>
                <td>{application.cardRequestID}</td>
                <td>{application.salesManagerName}</td>
                <td>{application.monthlySalary}</td>
                <td>{application.applicationStatus}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};
