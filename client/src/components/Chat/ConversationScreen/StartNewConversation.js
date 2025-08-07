import { useSelector } from "react-redux";
import UserAvatar from "../../User/UserAvatar";

const StartNewConversation = ({ clickSendMessageHandler }) => {

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const { displayPicture: otherUserDisplayPicture, displayName } = currentChatPreview || {};
    const myProfile = useSelector(state => state.myProfile);
    const activeChatTab = useSelector(state => state.activeChatTab);

    const isDirectMessage = activeChatTab.chatMode === 'DIRECT_MESSAGE';

    const sayHelloButtonClickHandler = () => {
        let message;
        if (activeChatTab.chatMode == 'DIRECT_MESSAGE') {
            message = `Hello ${displayName}! 👋`;
        } else if (activeChatTab.chatMode == 'GROUP_MESSAGE') {
            message = `Hello all! 👋`;
        } else {
            message = `Hello there! 👋`;
        }
        clickSendMessageHandler({ message });
    }

    return (currentChatPreview && myProfile) ? (<div id='start-new-conversation' className='FCCE'>
        {isDirectMessage && <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={myProfile.displayPicture} />
            <UserAvatar displayPicture={otherUserDisplayPicture} />
        </div>}
        <button id='say-hello-button' className='mT15' onClick={sayHelloButtonClickHandler}>
            Say Hello <i className="fa fa-hand" id='wave-hands'></i>
        </button>
    </div>) : null;
};

export default StartNewConversation;