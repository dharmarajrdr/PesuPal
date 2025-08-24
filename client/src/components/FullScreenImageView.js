import './FullScreenImageView.css';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { hideFullScreenImage, showImageAt } from '../store/reducers/FullScreenImageSlice';

const ResourceNotFound = () => (
    <div className='error-message'>
        <i className='fa fa-exclamation-triangle'></i>
        <span>Resource Not Found</span>
    </div>
);

const FullScreenImageView = () => {

    const dispatch = useDispatch();
    const [showError, setShowError] = useState(false);

    const { mediaUrls, currentIndex } = useSelector(state => state.fullScreenImage);
    const [mediaUrl, setMediaUrl] = useState(mediaUrls[currentIndex]);

    const showPreviousNextIcons = mediaUrls.length > 1;

    useEffect(() => {
        setMediaUrl(mediaUrls[currentIndex]);
    }, [currentIndex, mediaUrls]);

    const toggleImageViewer = (e, toIndex) => {
        e.stopPropagation();
        if (toIndex >= 0 && toIndex < mediaUrls.length) {
            dispatch(showImageAt(toIndex));
        }
    }

    const closeFullScreenImage = (e) => {
        e.stopPropagation();
        if (e.target.id === 'full-screen-ticket-attachment-overlay') {
            dispatch(hideFullScreenImage());
        }
    }

    const enableLeftArrow = currentIndex > 0, enableRightArrow = currentIndex < mediaUrls.length - 1;

    return mediaUrls.length > 0 ? (
        <div className='entire-screen-overlay FRCB' id='full-screen-ticket-attachment-overlay' onClick={closeFullScreenImage}>
            {showPreviousNextIcons && <div className={`FRCC full-screen-image-toggle-icons ${!enableLeftArrow ? 'disabled' : ''}`} onClick={(e) => toggleImageViewer(e, currentIndex - 1)}><i className='fa fa-chevron-left'></i></div>}
            <div id='full-screen-image-viewer' className='centerMe FRCC'>
                {showError ? <ResourceNotFound /> : (
                    <img src={mediaUrl} onError={() => setShowError(true)} />
                )}
            </div>
            {showPreviousNextIcons && <div className={`FRCC full-screen-image-toggle-icons ${!enableRightArrow ? 'disabled' : ''}`} onClick={(e) => toggleImageViewer(e, currentIndex + 1)}><i className='fa fa-chevron-right'></i></div>}
        </div>
    ) : null;
}

export default FullScreenImageView