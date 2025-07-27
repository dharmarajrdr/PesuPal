import './ChatInputUserArchived.css';

const ChatInputUserArchived = ({ displayName, message }) => {
    message = message || `${displayName} is no longer part of this organization.`;
    return (
        <div id='chat-input-user-archived' className='w100 FRCC p20'>
            <i className="fa-solid fa-user-slash mR5"></i>
            <p>{message}</p>
        </div>
    )
}

export default ChatInputUserArchived