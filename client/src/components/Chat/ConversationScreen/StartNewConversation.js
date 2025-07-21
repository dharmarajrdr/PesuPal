import UserAvatar from "../../User/UserAvatar";

const StartNewConversation = ({ activeChatPreview }) => {

    const { currentUser, otherUser } = activeChatPreview;
    return <div id='start-new-conversation' className='FCCE'>
        <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={currentUser.displayPicture} />
            <UserAvatar displayPicture={otherUser.displayPicture} />
        </div>
        <button id='say-hello-button' className='mT15'>
            Say hello <i className="fa fa-hand" id='wave-hands'></i>
        </button>
    </div>
};

export default StartNewConversation;