import React, { useState } from 'react';
import useWebSocket from '../../../WebSocket';
import utils from '../../../utils';

const ChatBox = () => {

    const currentUserId = sessionStorage.getItem('user-id'); // Default to 1 if not set
    const receiverId = currentUserId == '1' ? '2' : '1'; // Example receiver ID, can be dynamic based on the chat
    const [message, setMessage] = useState('');
    const [chatLog, setChatLog] = useState([]);

    const token = utils.parseCookie().get('token');
    const orgId = sessionStorage.getItem('org-id');

    const { sendMessage } = useWebSocket({
        userId: currentUserId,
        onPrivateMessage: (msg) => {
            setChatLog((prev) => [...prev, msg]);
        },
        onGroupMessage: (msg) => {
            setChatLog((prev) => [...prev, msg]);
        },
    });

    const handleSend = () => {
        const payload = {
            orgId: sessionStorage.getItem('org-id'),
            chatId: 'room123',
            senderId: currentUserId,
            receiverId: receiverId,
            message,
            isGroupMessage: false,
        };

        sendMessage('/app/chat.sendMessage', payload);
        setMessage('');
    };

    return (
        <div>
            <div>
                {chatLog.map((msg, index) => (
                    <div key={index}>
                        <strong>{msg.senderId}:</strong> {msg.message}
                    </div>
                ))}
            </div>
            <input
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type here"
            />
            <button onClick={handleSend}>Send</button>
        </div>
    );
};

export default ChatBox;
