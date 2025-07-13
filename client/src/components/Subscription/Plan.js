import React from 'react'
import './Plan.css';

const Plan = ({ plan }) => {

    const { id, name, price, interval, code, active, description, numberOfDays, features, highlight } = plan;

    return active ? (
        <div className='plan FCCS bgFFF h100' id={id}>
            {/* Recommended tag */}
            {highlight && <p className='recommended-plan ribbon'>{highlight}</p>}
            <h4 className='plan-code black-text'>{code}</h4>
            <div className='FCCC plan-price'>
                <h5 className='black-text mL5'>{interval}</h5>
                <div className='FRCC w100'>
                    <h4 className='px10 black-text'>₹</h4>
                    <h1 className='black-text'>{price}</h1>
                </div>
            </div>
            <h5 className='black-text plan-validity-days'>Validity {numberOfDays} days</h5>
            <button className='tryButton bgFFF' title={description}>
                Try {name}
            </button>
            <ul className='plan-features'>
                {features.map((feature, index) => <li className='black-text'>
                    <i className="fa-solid fa-square-check black-text"></i>{feature}
                </li>)}
            </ul>
        </div>
    ) : null;
}

export default Plan