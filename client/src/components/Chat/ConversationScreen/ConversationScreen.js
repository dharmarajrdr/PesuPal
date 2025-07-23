import { useEffect, useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { clearCurrentChatPreview, setCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import PermissionDenied from '../../Auth/PermissionDenied';
import ChatInputUserArchived from './ChatInputUserArchived';
import { setActiveChatTab } from '../../../store/reducers/ActiveChatTabSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { setShowChatHeaderOptionsModal } from '../../../store/reducers/ShowChatHeaderOptionsModalSlice';

const ConversationScreen = ({ activeTabName }) => {

  const { chatId } = useParams();

  const dispatch = useDispatch();

  dispatch(setActiveChatTab(activeTabName));

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(25);
  const [permissionDenied, setPermissionDenied] = useState(false);
  const [messages, setMessages] = useState([]);
  const [retrievingChat, setRetrievingChat] = useState(true);
  const [pivotMessageId, setPivotMessageId] = useState(null);

  const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
  const activeChatTab = useSelector(state => state.activeChatTab);
  const myProfile = useSelector(state => state.myProfile) || {};
  const { displayName, userId, active } = currentChatPreview || {};

  const { sendMessage } = useWebSocket({
    userId: myProfile.id,
    onPrivateMessage: (msg) => {
      setMessages((prev) => [...prev, msg]);
    },
    onGroupMessage: (msg) => {
      setMessages((prev) => [...prev, msg]);
    },
    onError: (error) => {
      dispatch(showPopup({ message: error, type: 'error' }));
    },
    onMessageDelivery: (msg) => {
      setMessages((prev) => [...prev, msg]);
    }
  });

  const readAllMessages = ({ chatId }) => {

    const { readAllMessagesApi } = activeChatTab || {};

    apiRequest(`${readAllMessagesApi}/${chatId}/read_all`, "PUT").then(() => {
      // TODO: Inform the receiver that messages have been read via WebSocket
    }).catch(({ message }) => {
      dispatch(showPopup({ message, type: 'error' }));
    });
  }

  const clickSendMessageHandler = ({ message }) => {

    console.log({ myProfile });

    const payload = {
      orgId: sessionStorage.getItem('org-id'),
      chatId: 'room123',
      "createdAt": new Date(),
      sender: myProfile,
      senderId: myProfile.id,
      receiverId: userId,
      message,
      isGroupMessage: false,
      readReceipt: "SENT",
      reactions: {},
      directMessageMediaFiles: []
    };

    sendMessage('/app/chat.sendMessage', payload);
  };

  const getChatPreview = (chatId) => {

    const isFirstLoad = true; // since chatId changed
    const pivot = isFirstLoad ? null : pivotMessageId;

    const { chatPreviewApi, retrieveConversationApi, readAllMessagesApi } = activeChatTab || {};

    if (!chatPreviewApi) { return; }

    apiRequest(`${chatPreviewApi}/${chatId}`, "GET").then(({ data }) => {

      setPermissionDenied(false);
      dispatch(setCurrentChatPreview(data));

      apiRequest(`${retrieveConversationApi}/${chatId}?page=${page}&size=${size}${pivot ? `&pivot_message_id=${pivot}` : ''}`, "GET").then(({ data }) => {
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

    dispatch(setShowChatHeaderOptionsModal(false));
    dispatch(clearCurrentChatPreview());
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
        {active ? <ChatInput clickSendMessageHandler={clickSendMessageHandler} /> : <ChatInputUserArchived displayName={displayName} />}
      </>}
    </div>
  ) : null;
}

export default ConversationScreen