import useWebSocket from "./WebSocket";
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from "./store/reducers/PopupSlice";
import { updateRecentChat } from "./components/Chat/ConversationScreen/updateRecentChat";

export default function useChatWebSocket({ onMessageSound }) {

    const dispatch = useDispatch();
    const myProfile = useSelector((state) => state.myProfile);

    const { DirectMessage, GroupMessage } = useWebSocket({
        userId: myProfile?.id,
        onPrivateMessage: (msg) => {
            updateRecentChat(dispatch, msg, "message-received");
            onMessageSound && onMessageSound();
        },
        onGroupMessage: (msg) => {
            updateRecentChat(dispatch, msg, "message-received");
            onMessageSound && onMessageSound();
        },
        onError: ({ message }) => {
            dispatch(showPopup({ message, type: "error" }));
        },
        onMessageDelivery: (msg) => {
            updateRecentChat(dispatch, msg, "message-delivered");
        },
        onTyping: () => {
            // TODO: handle typing indicator globally
        },
    });

    return { DirectMessage, GroupMessage };
}
