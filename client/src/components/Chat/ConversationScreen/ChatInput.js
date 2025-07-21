import { useState } from 'react';
import './ChatInput.css'

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const [message, setMessage] = useState('');

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
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