import { useRef, useState } from 'react';
import './ChatInput.css'
import EmojiPicker from 'emoji-picker-react';
import { useSelector } from 'react-redux';

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const [message, setMessage] = useState('');
    const [showEmojiPicker, setShowEmojiPicker] = useState(false);

    const chatId = useSelector((state) => state.chatId);

    const chatInputRef = useRef(null);

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
            setMessage("");
            setShowEmojiPicker(false);
            chatInputRef.current.focus();
            // fileInputRef?.current?.value = '';
        }
    };

    const onEmojiClick = (emojiData, event) => {

        setMessage((prevMessage) => prevMessage + emojiData.emoji);
    };

    const emojiPickerClickHandler = () => {

        setShowEmojiPicker(!showEmojiPicker);
        if (showEmojiPicker) {
            chatInputRef.current.focus();
        } else {
            chatInputRef.current.blur();
        }
    }


    return (
        <div className="chat-input w100 FRSS">
            <textarea ref={chatInputRef} type="text" value={message} autoFocus onChange={(e) => setMessage(e.target.value)} placeholder="Type your message..." />
            {/* <input type="file" ref={fileInputRef} className="file-upload" /> */}
            {showEmojiPicker && <div id='emoji-picker'>
                <EmojiPicker onEmojiClick={onEmojiClick} />
            </div>}
            <button onClick={emojiPickerClickHandler} className="emoji-button">
                <i className='fa fa-smile' />
            </button>
            <button onClick={handleSend} className="send-button">
                <i className='fa fa-paper-plane' />
            </button>
        </div>
    );
};


export default ChatInput