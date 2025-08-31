import { addMessage } from "../../../store/reducers/ConversationSlice";
import { moveRecentChatToTop, updateOrAddRecentChat } from "../../../store/reducers/RecentChatsSlice";
import utils from "../../../utils";

export const updateRecentChat = (dispatch, msg, type, activeChatTab, chatId) => {

    console.log(`Received message via WebSocket: `, msg);

    const { chatMode, message, sender, receiver, group, messageType } = msg || {};
    if (chatMode !== activeChatTab.chatMode) return;

    const isChatOpen = chatId == msg.chatId;
    const isGroupChat = chatMode === "GROUP_MESSAGE";
    const isMessageReceived = type === "message-received";

    const recentMessage = {
        chatId: msg.chatId,
        recentMessage: {
            message,
            media: false,
            createdAt: utils.convertTime(msg.createdAt, 12),
            messageType,
        },
    };

    if (isMessageReceived) {
        recentMessage.name = isGroupChat ? group?.name : sender?.displayName;
        recentMessage.status = sender?.status;
        recentMessage.recentMessage.sender = sender?.displayName;
        recentMessage.image = isGroupChat
            ? group?.displayPicture
            : sender?.displayPicture;
    } else {
        recentMessage.name = receiver?.displayName;
        recentMessage.status = receiver?.status;
        recentMessage.recentMessage.sender = receiver?.displayName;
        recentMessage.image = receiver?.displayPicture;
    }

    if (isChatOpen) {
        console.log("User is in the chat → rendering");
        dispatch(addMessage(msg));
    } else {
        console.log(`Chat not open → increment unread for chatId: ${msg.chatId}`);
        recentMessage.number_of_unread_messages = 1;
    }

    dispatch(updateOrAddRecentChat({ chatId: msg.chatId, recentMessage }));
    dispatch(moveRecentChatToTop(msg.chatId));
};
