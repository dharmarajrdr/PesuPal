import ChatInputGroupArchived from './ChatInputGroupArchived'
import ChatInputUserArchived from './ChatInputUserArchived'
import ChatInput from './ChatInput'
import GroupChatInactiveUser from './GroupChatInactiveUser'
import PostMessagePermissionDenied from './PostMessagePermissionDenied'

const ChatFooter = ({ active, messagePostable, groupActive, currentTab, displayName, clickSendMessageHandler }) => {

    if (currentTab === 'groupMessage') {
        if (!active) {
            return <GroupChatInactiveUser />
        } else if (!groupActive) {
            return <ChatInputGroupArchived />
        } else if (!messagePostable) {
            return <PostMessagePermissionDenied />;
        }
    } else {
        if (!active) {
            return <ChatInputUserArchived displayName={displayName} />
        }
    }
    return <ChatInput clickSendMessageHandler={clickSendMessageHandler} />;
}

export default ChatFooter