import { useDispatch, useSelector } from 'react-redux';
import './PinnedMessagesOverlay.css';
import { useState } from 'react';
import Loader from '../../Loader';
import './PinnedMessagesOverlay.css';

const NoMessagesFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-thumbtack-slash mR5 w15' />
                No messages pinned yet
            </p>
        </div>
    )
}

const PinnedMessagesOverlay = ({ onClose }) => {

    const dispatch = useDispatch();
    const [loader, setLoader] = useState(false);
    const [messages, setMessages] = useState([]);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const { retrieveConversationApi } = activeChatTab || {};

    return (
        <div id="pinned-messages-overlay" className='entire-screen-overlay FRCE' onClick={(e) => {
            if (e.target.id === 'pinned-messages-overlay') {
                onClose();
            }
        }}>
            <div id='pinned-messages-container' className='FCCS w100'>
                <div className='FRCB w100' id='pinned-messages-header'>
                    <h2 id='pinned-messages-title'>Pinned Messages {messages.length > 0 ? `(${messages.length})` : null}</h2>
                </div>
                {loader ? <Loader /> : <NoMessagesFound />}
            </div>
        </div>
    )
}

export default PinnedMessagesOverlay;