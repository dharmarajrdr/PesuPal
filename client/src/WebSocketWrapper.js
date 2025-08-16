import { useDispatch, useSelector } from "react-redux";
import { showPopup } from "./store/reducers/PopupSlice";
import { WebSocketProvider } from "./WebSocketProvider";
import { moveRecentChatToTop, updateOrAddRecentChat } from "./store/reducers/RecentChatsSlice";
import utils from "./utils";
import { addMessage } from "./store/reducers/ConversationSlice";

const WebSocketWrapper = ({ children }) => {

    const dispatch = useDispatch();
    const chatId = useSelector(state => state.chatId);
    const [currentChatId, setCurrentChatId] = useState(chatId);
    const activeChatTab = useSelector(state => state.activeChatTab);

    const updateRecentChat = (msg) => {

        console.log({ chatId });

        const { chatMode, message, sender } = msg || {};

        if (chatMode !== activeChatTab.chatMode) {
            return; // If the message is not in the current chat mode, ignore it
        }

        const isChatOpen = chatId == msg.chatId;

        const recentMessage = {
            chatId: msg.chatId,
            recentMessage: {
                sender: sender.displayName,
                message: message,
                media: false,
                createdAt: utils.convertTime(msg.createdAt, 12)
            }
        };

        console.log(`Received message in chatId: ${msg.chatId}, and user is ${isChatOpen ? 'in the chat' : 'not in the chat'}. He is actually in.`);

        if (isChatOpen) {	// User is waiting for a response

            console.log(`Since user is in the chat, rendering the message in the chat`);

            dispatch(addMessage(msg));

        } else {	// If the chat is not open, then show the number of unread messages

            console.log(`So, showing the number of unread messages for chatId: ${msg.chatId}`);

            Object.assign(recentMessage, { number_of_unread_messages: 1 });
        }

        dispatch(updateOrAddRecentChat({ 'chatId': msg.chatId, recentMessage }));

        console.log(`Updating recent chat for chatId: ${msg.chatId}`);

        dispatch(moveRecentChatToTop(msg.chatId));

        console.log(`Moved recent chat to top for chatId: ${msg.chatId}`);

    }

    return (
        <WebSocketProvider
            onPrivateMessage={(msg) => {
                console.log("Private message received:", msg);
                updateRecentChat(msg);
            }}
            onGroupMessage={(msg) => {
                console.log("Group message received:", msg);
                updateRecentChat(msg);
            }}
            onError={({ message }) => {
                console.error("WebSocket error:", message);
                dispatch(showPopup({ message, type: "error" }));
            }}
            onMessageDelivery={(msg) => {
                updateRecentChat(msg);
                console.log(`Message delivery update for chatId:`, msg);
            }}
            onTyping={(msg) => {
                console.log("Typing...", msg);
            }}
        >
            {children}
        </WebSocketProvider>
    );
};

export default WebSocketWrapper;
