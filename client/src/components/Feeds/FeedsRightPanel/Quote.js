import './Quote.css';
import Loader from '../../Loader';
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../http_request';

const Quote = () => {

    const [quote, setQuote] = useState(null);
    const [author, setAuthor] = useState(null);
    const [loading, setLoading] = useState(true);

    const fallbackResponse = {
        quote: "One day you will leave this world behind, so live a life you will remember.",
        author: "Avicii"
    }

    useEffect(() => {
        setLoading(true);
        apiRequest(`/api/v1/feeds/quote-of-the-day`).then(({ data }) => {
            const { quote, author } = data || {};
            setQuote(quote);
            setAuthor(author);
            setLoading(false);
        }).catch(() => {
            setQuote(fallbackResponse.quote);
            setAuthor(fallbackResponse.author);
            setLoading(false);
        });
    }, []);

    return (
        <div id='Quote' className='w100'>
            <p id='title' className='w100 selectNone'>
                <i className='fa-solid fa-quote-left w20 mR5' style={{ color: 'orange' }} ></i>Quote of the day
            </p>
            {loading ? <div className='FRCC w100'><Loader /></div> : <div className='FCSS'>
                <p id='QuoteText'>{quote}</p>
                <a href={`https://www.google.com/search?q=${author}`} target='_blank' rel='noopener noreferrer' className='w100 color777 mT10 italic' style={{ textAlign: 'right', fontSize: '12px' }}>- {author}</a>
            </div>}
        </div>
    )
}

export default Quote