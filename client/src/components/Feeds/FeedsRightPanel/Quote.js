import React from 'react'
import './Quote.css'

const Quote = ({ quote, author }) => {
    return (
        <div id='Quote' className='w100'>
            <p id='title' className='w100 selectNone'>
                <i className='fa-solid fa-quote-left w20 mR5' style={{ color: 'orange' }} ></i>Quote of the day
            </p>
            <div className='FCSS'>
                <p id='QuoteText'>{quote}</p>
                <i className='w100 color777 mT10' style={{ textAlign: 'right', fontSize: '12px' }}>- {author}</i>
            </div>
        </div>
    )
}

export default Quote