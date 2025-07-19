import React, { useEffect } from 'react';
import useWebSocket from '../../../WebSocket';

const ChatReceiver = () => {

    const currentUserId = sessionStorage.getItem('user-id'); // Default to '1' if not set
    const { sendMessage } = useWebSocket({
        userId: currentUserId,

        onPrivateMessage: (msg) => {
            console.log(`[PRIVATE] Message received for ${currentUserId}:`, msg);
        },

        onGroupMessage: (msg) => {
            console.log(`[GROUP] Message received in group:`, msg);
        },
    });

    useEffect(() => {
        console.log(`Receiver (${currentUserId}) is ready to receive messages`);
    }, [currentUserId]);

    return (
        <div>
            <h3>Receiver Panel for User: {currentUserId}</h3>
        </div>
    );
};

export default ChatReceiver;
