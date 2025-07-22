import './ChatInputUserArchived.css';

const ChatInputUserArchived = ({ displayName }) => {
    return (
        <div id='chat-input-user-archived' className='w100 FRCC p20'>
            <i className="fa-solid fa-user-slash mR5"></i>
            <p><b>{displayName}</b> is no longer part of this org.</p>
        </div>
    )
}

export default ChatInputUserArchived