import { useRef, useState } from 'react';
import './ChatInput.css'
import EmojiPicker from 'emoji-picker-react';
import { useDispatch, useSelector } from 'react-redux';
import AttachmentPreview from '../AttachmentPreview';
import { showPopup } from '../../../store/reducers/PopupSlice';

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const maxFilesSendable = 10;
    const [message, setMessage] = useState('');
    const [files, setFiles] = useState([]);
    const [showEmojiPicker, setShowEmojiPicker] = useState(false);

    const chatId = useSelector((state) => state.chatId);

    const chatInputRef = useRef(null);
    const fileInputRef = useRef(null);

    const dispatch = useDispatch();

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
            setMessage("");
            setShowEmojiPicker(false);
            chatInputRef.current.focus();
            // fileInputRef?.current?.value = "";
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

    const allowedTypes = ['image/png', 'image/jpeg', 'image/gif', 'video/mp4', 'audio/mpeg', 'application/pdf'];

    const handleFileChange = (e) => {

        const selectedFiles = Array.from(e.target.files);
        if (selectedFiles.length === 0) return;

        if (selectedFiles.length > maxFilesSendable) {
            return dispatch(showPopup({ message: `You can only upload a maximum of ${maxFilesSendable} files.`, type: 'error' }));
        }

        const filtered = selectedFiles.filter((file) =>
            allowedTypes.some((type) => file.type.includes(type))
        );

        if (filtered.length < selectedFiles.length) {
            return dispatch(showPopup({ message: "Some files were not allowed based on file type restrictions.", type: 'error' }));
        }

        const withPreview = filtered.map((file) => ({
            file,
            preview: file.type.startsWith("image")
                ? URL.createObjectURL(file)
                : null
        }));

        setFiles(withPreview);
        e.target.value = ""; // Reset file input
    };

    return (
        <div className="chat-input w100 FRSS">
            <textarea ref={chatInputRef} type="text" value={message} autoFocus onChange={(e) => setMessage(e.target.value)} placeholder="Type your message..." />
            <AttachmentPreview files={files} setFiles={setFiles} />
            {showEmojiPicker && <div id='emoji-picker'>
                <EmojiPicker onEmojiClick={onEmojiClick} />
            </div>}
            <button onClick={() => fileInputRef.current.click()} id="insert-file-button">
                <i className='fa fa-plus w20' />
                <input multiple type="file" ref={fileInputRef} style={{ display: 'none' }} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} />
            </button>
            <button onClick={emojiPickerClickHandler} id="emoji-button">
                <i className='fa fa-smile w20' />
            </button>
            <button onClick={handleSend} id="send-button">
                <i className='fa fa-paper-plane w20' />
            </button>
        </div>
    );
};


export default ChatInput