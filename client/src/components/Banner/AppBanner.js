import './AppBanner.css';
import { useEffect, useState } from 'react';

const AppBanner = ({ appBannerRef }) => {

    const messages = [
        // 'PesuPal will be down for maintenance on 15th October 2023 from 10:00 AM to 12:00 PM IST. We apologize for the inconvenience.',
        // 'Warning: Please do not share your PesuPal login credentials with anyone. PesuPal will never ask for your password.',
    ];

    const slideInterval = 10000; // 10 seconds

    const [currentMessageId, setCurrentMessageId] = useState(0);
    const [showSliderIcons, setShowSliderIcons] = useState(false);

    const previousMessageSlideHandler = () => {
        setCurrentMessageId((prevId) => (prevId - 1 + messages.length) % messages.length);
    }

    const nextMessageSlideHandler = () => {
        setCurrentMessageId((prevId) => (prevId + 1) % messages.length);
    }

    useEffect(() => {

        setShowSliderIcons(messages.length > 1);

        if (messages.length > 1) {
            const interval = setInterval(() => {
                setCurrentMessageId((prevId) => (prevId + 1) % messages.length);
            }, slideInterval);
            return () => clearInterval(interval);
        }
    }, []);

    return messages.length ? (
        <div id='app-banner' className='FRCB' ref={appBannerRef}>
            {showSliderIcons && <div className='slider-icon' onClick={previousMessageSlideHandler}>
                <i className='fa fa-chevron-left' aria-hidden="true"></i>
            </div>}
            <span className='w100 alignCenter'>
                <i className='fa fa-bullhorn w15' aria-hidden="true"></i>
                <span>{messages[currentMessageId]}</span>
            </span>
            {showSliderIcons && <div className='slider-icon' onClick={nextMessageSlideHandler}>
                <i className='fa fa-chevron-right' aria-hidden="true"></i>
            </div>}
        </div>
    ) : null;
}

export default AppBanner