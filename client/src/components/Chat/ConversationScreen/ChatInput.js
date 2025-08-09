import { useRef, useState } from 'react';
import './ChatInput.css'
import { useSelector } from 'react-redux';

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const [message, setMessage] = useState('');

    const chatId = useSelector((state) => state.chatId);

    const chatInputRef = useRef(null);

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
            setMessage("");
            chatInputRef.current.focus();
            // fileInputRef?.current?.value = '';
        }
    };

    return (
        <div className="chat-input w100 FRSS">
            <textarea ref={chatInputRef} type="text" value={message} autoFocus onChange={(e) => setMessage(e.target.value)} placeholder="Type your message..." />
            {/* <input type="file" ref={fileInputRef} className="file-upload" /> */}
            <button onClick={handleSend} className="send-button">
                <i className='fa fa-paper-plane' />
            </button>
        </div>
    );
};


export default ChatInput