import React from 'react';
import './Loader.css';

const Loader = ({ message = "Fetching data from server..." }) => {
    return (
        <div className="loader-container">
            <div className="dot-loader">
                <div></div><div></div><div></div>
            </div>
            <p className="loader-text">{message}</p>
        </div>
    );
};

export default Loader;
