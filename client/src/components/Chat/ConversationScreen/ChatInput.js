import { useRef, useState } from 'react';
import './ChatInput.css'

const ChatInput = ({ onSend }) => {
    const [message, setMessage] = useState('');
    const fileInputRef = useRef();

    const handleSend = () => {
        if (message.trim()) {
            onSend({ message, file: fileInputRef.current.files[0] });
            setMessage('');
            fileInputRef.current.value = '';
        }
    };

    return (
        <div className="chat-input">
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type your message..."
            />
            <input type="file" ref={fileInputRef} className="file-upload" />
            <button onClick={handleSend} className="send-button">
                <i className='fa fa-paper-plane' />
            </button>
        </div>
    );
};


export default ChatInput