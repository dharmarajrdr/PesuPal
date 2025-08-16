import React, { createContext, useContext } from "react";
import { useSelector } from "react-redux";
import useWebSocket from "./WebSocket";

const WebSocketContext = createContext(null);

export const WebSocketProvider = ({
    children,
    onPrivateMessage,
    onGroupMessage,
    onError,
    onMessageDelivery,
    onTyping
}) => {
    const myProfile = useSelector(state => state.myProfile) || {};

    const { DirectMessage, GroupMessage } = useWebSocket({
        userId: myProfile.id,
        onPrivateMessage,
        onGroupMessage,
        onError,
        onMessageDelivery,
        onTyping
    });

    return (
        <WebSocketContext.Provider value={{ DirectMessage, GroupMessage }}>
            {children}
        </WebSocketContext.Provider>
    );
};

export const useWebSocketContext = () => useContext(WebSocketContext);
