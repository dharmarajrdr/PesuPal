import { useSelector } from "react-redux";
import UserAvatar from "../../User/UserAvatar";

const StartNewConversation = ({ clickSendMessageHandler }) => {

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const { currentUser, otherUser, chatId } = currentChatPreview || {};
    const { displayName: otherUserName } = otherUser || {};

    const sayHelloButtonClickHandler = () => {
        clickSendMessageHandler({ message: `Hello ${otherUserName || 'there'}! 👋` });
    }

    return <div id='start-new-conversation' className='FCCE'>
        <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={currentUser?.displayPicture} />
            <UserAvatar displayPicture={otherUser?.displayPicture} />
        </div>
        <button id='say-hello-button' className='mT15' onClick={sayHelloButtonClickHandler}>
            Say Hello <i className="fa fa-hand" id='wave-hands'></i>
        </button>
    </div>
};

export default StartNewConversation;