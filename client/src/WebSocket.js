import { useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import utils from './utils';

const subscribe = {

    'using': (stompClient) => {

        return {
            'events': (arrayOfEvents) => {
                arrayOfEvents.forEach(({event, callback}) => {
                    stompClient.subscribe(event, (msg) => {
                        const payload = JSON.parse(msg.body);
                        callback(payload);
                    });
                });
            }
        }
    }
}

const useWebSocket = ({ onPrivateMessage, onGroupMessage, onError, onMessageDelivery, userId }) => {
    
    const stompClientRef = useRef(null);
    
    useEffect(() => {
        
        const stompClient = new Client({
            webSocketFactory: () => new SockJS(`${utils.serverDomain}/ws`),
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            onConnect: () => {

                subscribe.using(stompClient).events([
                    { event: `/topic/direct-message.${userId}`, callback: onPrivateMessage },
                    { event: `/topic/group-message.${userId}`, callback: onGroupMessage },
                    { event: `/queue/errors.${userId}`, callback: onError },
                    { event: `/topic/message-delivery.${userId}`, callback: onMessageDelivery }
                ]);

            },
        });

        stompClient.activate();
        stompClientRef.current = stompClient;

        return () => {
            stompClient.deactivate();
        };

    }, [userId]);

    const DirectMessage = {

        send: (message) => {
            if (stompClientRef.current?.connected) {
                stompClientRef.current.publish({
                    destination: `/app/chat.sendMessage`,
                    body: JSON.stringify(message),
                });
            }
        }
    }

    const GroupMessage = {
        send: (message) => {
            if (stompClientRef.current?.connected) {
                stompClientRef.current.publish({
                    destination: `/app/chat.sendGroupMessage`,
                    body: JSON.stringify(message),
                });
            }
        }
    }

    return { DirectMessage, GroupMessage };
};

export default useWebSocket;
