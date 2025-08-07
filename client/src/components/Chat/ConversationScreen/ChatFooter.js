import ChatInputGroupArchived from './ChatInputGroupArchived'
import ChatInputUserArchived from './ChatInputUserArchived'
import ChatInput from './ChatInput'
import GroupChatInactiveUser from './GroupChatInactiveUser'

const ChatFooter = ({ active, groupActive, currentTab, displayName, clickSendMessageHandler }) => {

    if (currentTab === 'groupMessage') {
        if (active) {
            if (!groupActive) {
                return <ChatInputGroupArchived />
            }
        } else {
            return <GroupChatInactiveUser />
        }
    } else {
        if (!active) {
            return <ChatInputUserArchived displayName={displayName} />
        }
    }
    return <ChatInput clickSendMessageHandler={clickSendMessageHandler} />;
}

export default ChatFooter