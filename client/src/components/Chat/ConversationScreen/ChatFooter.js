import ChatInputGroupArchived from './ChatInputGroupArchived'
import ChatInputUserArchived from './ChatInputUserArchived'
import ChatInput from './ChatInput'

const ChatFooter = ({ active, groupActive, currentTab, displayName, clickSendMessageHandler }) => {

    if (currentTab === 'groupMessage') {
        if (active) {
            if (!groupActive) {
                return <ChatInputGroupArchived />
            }
        } else {
            return <ChatInputUserArchived message="You are no longer part of this conversation." />
        }
    } else {
        if (!active) {
            return <ChatInputUserArchived displayName={displayName} />
        }
    }
    return <ChatInput clickSendMessageHandler={clickSendMessageHandler} />;
}

export default ChatFooter