import './FullScreenImageView.css';

const FullScreenImageView = ({ mediaUrl, onClose }) => {

    return mediaUrl ? (
        <div className='entire-screen-overlay' id='full-screen-ticket-attachment-overlay' onClick={(e) => {
            e.stopPropagation();
            if (e.target.id === 'full-screen-ticket-attachment-overlay') {
                onClose();
            }
        }}>
            <div id='full-screen-image-viewer' className='centerMe'>
                <img src={mediaUrl} alt='Resource Not Found' />
            </div>
        </div>
    ) : null;
}

export default FullScreenImageView