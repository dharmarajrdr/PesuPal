import MessageDeleted from './MessageDeleted';
import Message from './Message';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';
import { useSelector } from 'react-redux';

const ChatMessageImage = ({ fileUrl }) => {
    return fileUrl ? <div className="message-media">
        <img src={fileUrl} alt="Media" className="media-image cursP" />
    </div> : null;
}

const ChatMessageItem = ({ msg }) => {

    const { sender, deleted, createdAt, readReceipt, message, media } = msg;
    const { id, fileUrl } = media || {};

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const isCurrentUser = sender == currentChatPreview?.currentUser?.id;

    return (
        <div className='row w100 FRCS'>
            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                {deleted ? <MessageDeleted /> : <>
                    <ChatMessageImage fileUrl={fileUrl} />
                    <div className="message-content">
                        <Message html={message} />
                        <MessageActions />
                    </div>
                    <MessageMeta createdAt={createdAt} readReceipt={readReceipt} isCurrentUser={isCurrentUser} />
                </>}
            </div>
        </div>
    );
};

export default ChatMessageItem;