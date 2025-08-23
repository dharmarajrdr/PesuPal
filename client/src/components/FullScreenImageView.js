import { useState } from 'react';
import './FullScreenImageView.css';
import { useDispatch, useSelector } from 'react-redux';
import { hideFullScreenImage } from '../store/reducers/FullScreenImageSlice';

const FullScreenImageView = () => {

    const dispatch = useDispatch();
    const [showError, setShowError] = useState(false);

    const { mediaUrl } = useSelector(state => state.fullScreenImage);

    return mediaUrl ? (
        <div className='entire-screen-overlay' id='full-screen-ticket-attachment-overlay' onClick={(e) => {
            e.stopPropagation();
            if (e.target.id === 'full-screen-ticket-attachment-overlay') {
                dispatch(hideFullScreenImage());
            }
        }}>
            <div id='full-screen-image-viewer' className='centerMe FRCC'>

                {showError ? (
                    <div className='error-message'>
                        <i className='fa fa-exclamation-triangle'></i>
                        <span>Resource Not Found</span>
                    </div>
                ) : (
                    <img src={mediaUrl} onError={() => setShowError(true)} />
                )}
            </div>
        </div>
    ) : null;
}

export default FullScreenImageView