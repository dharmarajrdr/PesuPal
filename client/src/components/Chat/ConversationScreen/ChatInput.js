import { useState } from 'react';
import './ChatInput.css'
import { useDispatch, useSelector } from 'react-redux';
import { moveRecentChatToTop, updateRecentChat } from '../../../store/reducers/RecentChatsSlice';
import utils from '../../../utils';

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const [message, setMessage] = useState('');
    const dispatch = useDispatch();

    const chatId = useSelector((state) => state.chatId);

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
            /*{
                "id": 174,
                "createdAt": "2025-07-23T23:23:40.653724",
                "sender": {
                    "id": 1,
                    "displayName": "Dharmaraj",
                    "displayPicture": "https://avatars.githubusercontent.com/u/66894285?v=4",
                    "archived": false
                },
                "chatId": "1_2_1",
                "message": "678",
                "deleted": false,
                "readReceipt": "SENT",
                "reactions": {},
                "chatMode": "DIRECT_MESSAGE"
            }*/
            const recentMessage = {
                createdAt: utils.convertTime(new Date(), 12),
                media: false,
                message,
                readReceipt: "SENT",
                sender: "Me"
            };
            dispatch(updateRecentChat({ chatId, recentMessage }));  // issue here. fix this
            dispatch(moveRecentChatToTop(chatId));
            setMessage("");
            // fileInputRef?.current?.value = '';
        }
    };

    return (
        <div className="chat-input w100 FRSS">
            <textarea type="text" value={message} onChange={(e) => setMessage(e.target.value)} placeholder="Type your message..." />
            {/* <input type="file" ref={fileInputRef} className="file-upload" /> */}
            <button onClick={handleSend} className="send-button">
                <i className='fa fa-paper-plane' />
            </button>
        </div>
    );
};


export default ChatInput