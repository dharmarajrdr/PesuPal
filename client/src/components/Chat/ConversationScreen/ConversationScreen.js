import { useEffect, useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { setCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import PermissionDenied from '../../Auth/PermissionDenied';
import ChatInputUserArchived from './ChatInputUserArchived';

const readAllMessages = ({ chatId }) => {
  apiRequest(`/api/v1/direct-messages/${chatId}/read_all`, "PUT").then(() => {
    // TODO: Inform the receiver that messages have been read via WebSocket
  }).catch(({ message }) => {
    console.error(message);
  });
}

const ConversationScreen = () => {

  const { chatId } = useParams();

  const dispatch = useDispatch();

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(25);
  const [permissionDenied, setPermissionDenied] = useState(false);
  const [messages, setMessages] = useState([]);
  const [retrievingChat, setRetrievingChat] = useState(true);
  const [pivotMessageId, setPivotMessageId] = useState(null);

  const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
  const currentUser = currentChatPreview.currentUser || {};
  const otherUser = currentChatPreview.otherUser || {};

  const { sendMessage } = useWebSocket({
    userId: currentUser.id,
    onPrivateMessage: (msg) => {
      setMessages((prev) => [...prev, {
        "id": 101,
        "createdAt": new Date().toISOString(),
        "sender": msg.senderId,
        "receiver": msg.receiverId,
        "message": msg.message,
        "deleted": false,
        "readReceipt": "READ",
        "reactions": {},
        "directMessageMediaFiles": []
      }]);
    },
    onGroupMessage: (msg) => {
      // setChatLog((prev) => [...prev, msg]);
    },
  });

  const clickSendMessageHandler = ({ message }) => {

    const payload = {
      orgId: sessionStorage.getItem('org-id'),
      chatId: 'room123',
      senderId: currentUser.id,
      receiverId: otherUser.id,
      message,
      isGroupMessage: false,
    };

    sendMessage('/app/chat.sendMessage', payload);
    setMessages((prev) => [...prev, {
      "id": 101,
      "createdAt": new Date().toISOString(),
      "sender": currentUser.id,
      "receiver": otherUser.id,
      "message": message,
      "deleted": false,
      "readReceipt": "SENT",
      "reactions": {},
      "directMessageMediaFiles": []
    }]);
  };

  const getChatPreview = (chatId) => {

    const isFirstLoad = true; // since chatId changed
    const pivot = isFirstLoad ? null : pivotMessageId;

    apiRequest(`/api/v1/direct-messages/preview/${chatId}`, "GET").then(({ data }) => {

      setPermissionDenied(false);
      dispatch(setCurrentChatPreview(data));

      apiRequest(`/api/v1/direct-messages/${chatId}?page=${page}&size=${size}${pivot ? `&pivot_message_id=${pivot}` : ''}`, "GET").then(({ data }) => {
        setMessages(data);
        setRetrievingChat(false);

        // Update pivot to the last message’s ID
        if (data.length > 0) {
          setPivotMessageId(data.at(-1)?.id);
        }

      }).catch(({ message }) => {
        console.error(message);
        setRetrievingChat(false);
      });

    }).catch(({ message, statusCode }) => {

      setRetrievingChat(false);
      if (statusCode === 403) {
        setPermissionDenied(true);
      }

    });

  };

  useEffect(() => {

    dispatch(setChatId(chatId));
    setPivotMessageId(null); // reset state — this takes effect after render
    setRetrievingChat(true);
    getChatPreview(chatId);
    readAllMessages({ chatId });

  }, [chatId]);

  return currentChatPreview ? (
    <div id='ConversationScreen' className='FCSB'>
      {permissionDenied ? <PermissionDenied /> : <>
        <ChatHeader />
        <ChatMessages retrievingChat={retrievingChat} messages={messages} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
        {otherUser.archived ? <ChatInputUserArchived displayName={otherUser.displayName} /> : <ChatInput clickSendMessageHandler={clickSendMessageHandler} />}
      </>}
    </div>
  ) : null;
}

export default ConversationScreen