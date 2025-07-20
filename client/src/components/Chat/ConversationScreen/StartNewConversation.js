import UserAvatar from "../../User/UserAvatar";

const StartNewConversation = () => (
    <div id='start-new-conversation' className='FCCE'>
        <div className='FRCC' id='users-avatars'>
            <UserAvatar displayPicture={"/images/Users/user_8.jpg"} />
            <UserAvatar displayPicture={"/images/Users/user_6.jpg"} />
        </div>
        <button id='say-hello-button' className='mT15'>
            Say hello <i className="fa fa-hand" id='wave-hands'></i>
        </button>
    </div>
);

export default StartNewConversation;