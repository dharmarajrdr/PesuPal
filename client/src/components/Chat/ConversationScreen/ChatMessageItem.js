import MessageDeleted from './MessageDeleted';
import Message from './Message';
import MessageActions from './MessageActions';
import MessageMeta from './MessageMeta';
import { useSelector } from 'react-redux';

const ChatMessageItem = ({ msg }) => {

    const { sender, deleted, createdAt, readReceipt, message } = msg;

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const isCurrentUser = sender == currentChatPreview?.currentUser?.id;

    return (
        <div className='row w100 FRCS'>
            <div className={`message ${isCurrentUser ? 'sent' : 'received'}`}>
                {deleted ? <MessageDeleted /> : <>
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