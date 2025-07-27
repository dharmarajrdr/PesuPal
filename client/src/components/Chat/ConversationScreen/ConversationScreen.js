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
import { moveRecentChatToTop, updateOrAddRecentChat } from '../../../store/reducers/RecentChatsSlice';
import utils from '../../../utils';

const ConversationScreen = ({ activeTabName }) => {

	const { chatId } = useParams();

	console.log(`ConversationScreen: chatId = ${chatId}`);

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

	const updateRecentChat = (msg) => {

		console.log("Received message:", msg);

		const { chatMode, message, sender } = msg || {};

		if (chatMode !== activeChatTab.chatMode) {
			return; // If the message is not in the current chat mode, ignore it
		}

		console.log("user is in current chat mode:", activeChatTab.chatMode);

		const isChatOpen = chatId == msg.chatId;

		console.log(`Current chatId = ${chatId}. Message chatId = ${msg.chatId}. Is chat open? ${isChatOpen}`);

		const recentMessage = {
			chatId: msg.chatId,
			name: sender.displayName,
			image: sender.displayPicture,
			recentMessage: {
				sender: sender.displayName,
				message: message,
				media: false,
				createdAt: utils.convertTime(msg.createdAt, 12)
			}
		};

		if (!isChatOpen) {  // If the chat is not open, then show the number of unread messages

			console.log(`So, showing the number of unread messages for chatId: ${msg.chatId}`);

			Object.assign(recentMessage, { number_of_unread_messages: 1 });
		}

		if (isChatOpen) {	// User is waiting for a response

			console.log(`Since user is in the chat, rendering the message in the chat`);

			setMessages((prev) => [...prev, msg]);
		}

		dispatch(updateOrAddRecentChat({ 'chatId': msg.chatId, recentMessage }));

		console.log(`Updating recent chat for chatId: ${msg.chatId}`);

		dispatch(moveRecentChatToTop(msg.chatId));

		console.log(`Moved recent chat to top for chatId: ${msg.chatId}`);

	}

	const { DirectMessage, GroupMessage } = useWebSocket({
		userId: myProfile.id,
		onPrivateMessage: (msg) => {

			updateRecentChat(msg);
		},
		onGroupMessage: (msg) => {

			updateRecentChat(msg);
		},
		onError: (error) => {
			dispatch(showPopup({ message: error, type: 'error' }));
		},
		onMessageDelivery: (msg) => {

			updateRecentChat(msg);
		},
		onTyping: () => {

		}
	});

	const readAllMessages = ({ chatId }) => {

		const { readAllMessagesApi } = activeChatTab || {};

		apiRequest(`${readAllMessagesApi}/${chatId}/read-all`, "PUT").then(() => {

		}).catch(({ message }) => {
			dispatch(showPopup({ message, type: 'error' }));
		});
	}

	const clickSendMessageHandler = ({ message }) => {

		const payload = {
			message
		};

		if (activeChatTab.name === 'directMessage') {
			Object.assign(payload, { chatId });
			DirectMessage.send(payload);
		} else if (activeChatTab.name === 'groupMessage') {
			GroupMessage.send(payload);
		}
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
				<ChatMessages active={active} retrievingChat={retrievingChat} messages={messages} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
				{active ? <ChatInput clickSendMessageHandler={clickSendMessageHandler} /> : <ChatInputUserArchived displayName={displayName} />}
			</>}
		</div>
	) : null;
}

export default ConversationScreen