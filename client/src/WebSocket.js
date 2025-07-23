import { useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import utils from './utils';

const useWebSocket = ({ onPrivateMessage, onGroupMessage, onError, onMessageDelivery, userId }) => {
    const stompClientRef = useRef(null);
    useEffect(() => {
        const stompClient = new Client({
            webSocketFactory: () => new SockJS(`${utils.serverDomain}/ws`),
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            onConnect: () => {

                stompClient.subscribe(`/topic/user.${userId}`, (msg) => {
                    const payload = JSON.parse(msg.body);
                    onPrivateMessage(payload);
                });

                stompClient.subscribe(`/queue/errors.${userId}`, (error) => {
                    const { message } = JSON.parse(error.body);
                    onError(message);
                });

                stompClient.subscribe(`/topic/message-delivery.${userId}`, (msg) => {
                    const payload = JSON.parse(msg.body);
                    onMessageDelivery(payload);
                });

                // Add group subscription if needed
            },
        });

        stompClient.activate();
        stompClientRef.current = stompClient;

        return () => {
            stompClient.deactivate();
        };
    }, [userId]); // 👈 Only re-run when `userId` changes

    const sendMessage = (destination, message) => {
        if (stompClientRef.current?.connected) {
            stompClientRef.current.publish({
                destination,
                body: JSON.stringify(message),
            });
        }
    };

    return { sendMessage };
};

export default useWebSocket;
