import { useSelector } from "react-redux";
import UserAvatar from "../../User/UserAvatar";

const StartNewConversation = ({ clickSendMessageHandler }) => {

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const { displayPicture: otherUserDisplayPicture, displayName } = currentChatPreview || {};
    const myProfile = useSelector(state => state.myProfile);

    const sayHelloButtonClickHandler = () => {
        clickSendMessageHandler({ message: `Hello ${displayName || 'there'}! 👋` });
    }

    return (currentChatPreview && myProfile) ? (<div id='start-new-conversation' className='FCCE'>
        <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={myProfile.displayPicture} />
            <UserAvatar displayPicture={otherUserDisplayPicture} />
        </div>
        <button id='say-hello-button' className='mT15' onClick={sayHelloButtonClickHandler}>
            Say Hello <i className="fa fa-hand" id='wave-hands'></i>
        </button>
    </div>) : null;
};

export default StartNewConversation;