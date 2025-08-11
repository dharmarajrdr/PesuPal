import { useEffect, useState } from 'react';
import './TicketDetailView.css'
import Loader from '../Loader';
import PageNotFound from '../Auth/PageNotFound';
import utils from '../../utils';
import ReactQuill from 'react-quill';
import { apiRequest } from '../../http_request';
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { showPopup } from '../../store/reducers/PopupSlice';

const TicketDescription = ({ html }) => <div id='ticket-description' className="html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />

const SupportTicketComment = ({ comment }) => {

    const { id, createdAt, message, commentedBy } = comment || {};
    const { displayName, displayPicture } = commentedBy || {};
    return (
        <div className='support-ticket-comment w100 FCSB' key={id}>
            <div className='comment-header FRCS w100 mB10'>
                <img src={displayPicture} className='commented-by-display-picture img_20_20 mR10' />
                <span className='commented-by-name'>{displayName}</span>
                <span className='commented-by-date mL10 pL10'>{utils.convertDateAndTime(createdAt)}</span>
            </div>
            <div className='comment-message w100'>
                {message}
            </div>
        </div>
    );
};

const FullScreenImageView = ({ mediaUrl, onClose }) => {

    return mediaUrl ? (
        <div className='entire-screen-overlay' id='full-screen-ticket-attachment-overlay' onClick={(e) => {
            e.stopPropagation();
            if (e.target.id === 'full-screen-ticket-attachment-overlay') {
                onClose();
            }
        }}>
            <div id='full-screen-image-viewer' className='centerMe'>
                <img src={mediaUrl} alt='Full Screen' />
            </div>
        </div>
    ) : null;
}

const TicketDetailView = ({ ticket, ticketColor }) => {

    const dispatch = useDispatch();
    const [loader, setLoader] = useState(false);
    const [pageNotFound, setPageNotFound] = useState(false);
    const [ticketDetails, setTicketDetails] = useState(ticket);

    const params = useParams();
    const { ticketId: id } = params || {};

    useEffect(() => {
        if (ticket) {
            setTicketDetails(ticket);
            setLoader(false);
        } else {
            apiRequest(`/api/v1/support/ticket/${id}`, 'GET').then(({ data }) => {
                setTicketDetails(data);
                setLoader(false);
            }).catch(({ message }) => {
                setPageNotFound(true);
                dispatch(showPopup({ message, type: 'error' }));
            });
        }
    }, [])

    useEffect(() => {
        setTicketDetails(ticket);
        setLoader(false);
    }, [ticket]);

    const { ticketId, subject, description, status, createdAt, attachments } = ticketDetails || {};

    const [comments, setComments] = useState([]);
    const [fullScreenAttachment, setFullScreenAttachment] = useState(null);

    const viewAttachmentFullScreen = (mediaUrl) => {
        setFullScreenAttachment(mediaUrl);
    }

    const closeFullScreenView = () => {
        setFullScreenAttachment(null);
    }

    return <div id='ticket-detail-view-layout' className='FCSS w100 h100'>
        {
            loader ? <Loader /> :
                pageNotFound ? <PageNotFound /> : (
                    <div id='ticket-detail-view' className='FCSB'>
                        <div id='ticket-detail-header' className='FRSB'>
                            <div id='ticket-subject-id' className='FCSS'>
                                <p id='subject'>{subject}</p>
                                <span id='ticket-id'>#{ticketId}</span>
                            </div>
                            <div id='ticket-status-container' className='FRCE'>
                                <span id='ticket-status' style={{ backgroundColor: ticketColor?.[status] }}>{status}</span>
                            </div>
                        </div>
                        <TicketDescription html={description} />
                        <div id='ticket-attachments' className='FRSS w100 mT10'>
                            <FullScreenImageView mediaUrl={fullScreenAttachment} onClose={closeFullScreenView} />
                            {attachments?.map(({ mediaUrl, fileName }, index) => (
                                <div className='attachment' key={index} onClick={() => viewAttachmentFullScreen(mediaUrl)}>
                                    <img src={mediaUrl} alt={fileName} />
                                </div>
                            ))}
                        </div>
                        <span id='ticket-created-at'>Created on {utils.convertDateAndTime(createdAt)}</span>
                        <div id='ticket-comments' className='FCSB mT20'>
                            <h3>Comments({comments.length})</h3>
                            <ReactQuill theme="snow" className='w100' id='add-comment' placeholder='Add a comment...' />
                            {comments.map(comment => (
                                <SupportTicketComment key={comment.id} comment={comment} />
                            ))}
                        </div>
                    </div>
                )
        }
    </div>
}

export default TicketDetailView