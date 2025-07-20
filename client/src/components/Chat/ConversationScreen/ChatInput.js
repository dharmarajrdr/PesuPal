import './ChatInput.css'

const ChatInput = ({ clickSendMessageHandler, message, setMessage }) => {

    // const fileInputRef = useRef();

    const handleSend = () => {
        if (message.trim()) {
            clickSendMessageHandler({});
            setMessage('');
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