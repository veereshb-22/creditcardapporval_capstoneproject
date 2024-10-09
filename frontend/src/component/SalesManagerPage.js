import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import { SalesManagerCardApplication } from './SalesManagerCardApplication';
import {SalesManagerNavBar} from './SalesManagerNavBar';
import {SalesManagerApplicationStatus} from './SalesManagerApplicationStatus'
 const SalesManagerPage= () =>{
    const homeBackgroundStyle = {
        backgroundImage: 'url("https://wallpapercave.com/wp/wp6602995.jpg")',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
    };
    const contentStyle = {
        flex: 1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'flex-start',
        paddingTop: '60px',
    };

    return(
       
        <div  style={homeBackgroundStyle}>
        <SalesManagerNavBar />
         {/* Render the CustomerNavbar */}
          <Routes> CustomerCardApply
              <Route path="salesmanagerapplication" element={<SalesManagerCardApplication />} />
              <Route path="salesmanagerapplicationstatus" element={<SalesManagerApplicationStatus />} />
              
              <Route path="/" element={
                  <div className="d-flex justify-content-center align-items-center" style={{ height: '70vh' }}>
                      <h2 style={{color :'#f0f4f8'}}>Sales Manager Page</h2>
                    
                  </div>
              } />
             
          </Routes>
         
      </div>
    );
 }
 export default SalesManagerPage;