import './ConversationScreenPlaceholder.css';

const ConversationScreenPlaceholder = () => {
    return (
        <div id='conversation-screen-placeholder' className='FCCC h100'>
            <img src='/images/Background/chat.gif' />
            <div className='FCCC'>
                <p className='fs14'>Select a chat to start messaging. </p>
            </div>
        </div>
    )
}

export default ConversationScreenPlaceholder