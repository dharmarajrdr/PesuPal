import { useRef, useState } from 'react';
import './ChatInput.css'
import EmojiPicker from 'emoji-picker-react';
import { useDispatch, useSelector } from 'react-redux';
import AttachmentPreview from '../AttachmentPreview';
import { showPopup } from '../../../store/reducers/PopupSlice';
import SchedulePicker from './SchedulePicker';
import { apiRequest } from '../../../http_request';

const ChatInput = ({ clickSendMessageHandler }) => {

    // const fileInputRef = useRef();
    const maxFilesSendable = 10;
    const [message, setMessage] = useState('');
    const [files, setFiles] = useState([]);

    const [showEmojiPicker, setShowEmojiPicker] = useState(false);
    const [showSchedulePicker, setShowSchedulePicker] = useState(false);

    const chatId = useSelector((state) => state.chatId);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const { groupChatConfiguration } = currentChatPreview || {};
    const { scheduleMessage } = groupChatConfiguration || {};

    const chatInputRef = useRef(null);
    const fileInputRef = useRef(null);
    const scheduleDateTimePickerRef = useRef(null);

    const { retrieveConversationApi, chatMode } = activeChatTab || {};
    const messageScheduleable = chatMode === 'GROUP_MESSAGE' ? scheduleMessage : true;

    const dispatch = useDispatch();

    const focusChatInput = () => {
        chatInputRef.current.focus();
    };

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({ message });
            setMessage("");
            setShowEmojiPicker(false);
            focusChatInput();
            // fileInputRef?.current?.value = "";
        }
    };

    const onEmojiClick = (emojiData, event) => {

        setMessage((prevMessage) => prevMessage + emojiData.emoji);
    };

    const emojiPickerClickHandler = () => {

        setShowSchedulePicker(false);
        setShowEmojiPicker(!showEmojiPicker);
        if (showEmojiPicker) {
            focusChatInput();
        } else {
            chatInputRef.current.blur();
        }
    }

    const scheduleButtonClicked = () => {

        setShowEmojiPicker(false);
        setShowSchedulePicker(!showSchedulePicker);
        if (scheduleDateTimePickerRef.current) {
            scheduleDateTimePickerRef.current.showPicker?.(); // Opens the native picker (modern browsers)
            scheduleDateTimePickerRef.current.click(); // Fallback for older browsers
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

    const scheduleMessageHandler = (scheduledTime) => {

        const afterSchedule = () => {
            setMessage("");
            setFiles([]);
            setShowSchedulePicker(false);
            focusChatInput();
        }

        apiRequest(`${retrieveConversationApi}/schedule`, "POST", {
            message, chatId, 'scheduleAt': scheduledTime, 'messageStatus': 'SCHEDULED', 'token': sessionStorage.getItem('token')
        }).then(({ message }) => {
            afterSchedule();
            dispatch(showPopup({ message, type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return (
        <div className="chat-input w100 FRSS pR">
            <textarea ref={chatInputRef} type="text" value={message} autoFocus onChange={(e) => setMessage(e.target.value)} placeholder="Type your message..." />
            <AttachmentPreview files={files} setFiles={setFiles} clickSendMessageHandler={clickSendMessageHandler} />
            {showEmojiPicker && <div id='emoji-picker'>
                <EmojiPicker onEmojiClick={onEmojiClick} />
            </div>}
            {showSchedulePicker && message.trim().length > 0 && <SchedulePicker onSchedule={scheduleMessageHandler} showPicker={showSchedulePicker} setShowPicker={setShowSchedulePicker} />}
            <button onClick={() => fileInputRef.current.click()} id="insert-file-button" title='Insert File'>
                <i className='fa fa-plus w20' />
                <input multiple type="file" ref={fileInputRef} style={{ display: 'none' }} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} />
            </button>
            <button onClick={emojiPickerClickHandler} id="emoji-button" title='Emoji Picker'>
                <i className='fa-regular fa-smile w20' />
            </button>
            {messageScheduleable && <button onClick={scheduleButtonClicked} id="schedule-message-button" title='Schedule Message'>
                <i className='fa-regular fa-clock w20' />
            </button>}
            <button onClick={handleSend} id="send-button" title='Send Message' disabled={!message.trim() && files.length === 0}>
                <i className='fa-regular fa-paper-plane w20' />
            </button>
        </div>
    );
};


export default ChatInput