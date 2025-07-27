import { useEffect, useState } from 'react'
import './ConversationScreen.css';
import ChatHeader from './ChatHeader';
import ChatMessages from './ChatMessages';
import useWebSocket from '../../../WebSocket';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { clearCurrentChatPreview, setCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { setChatId } from '../../../store/reducers/ChatIdSlice';
import PermissionDenied from '../../Auth/PermissionDenied';
import { setActiveChatTab } from '../../../store/reducers/ActiveChatTabSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { setShowChatHeaderOptionsModal } from '../../../store/reducers/ShowChatHeaderOptionsModalSlice';
import { moveRecentChatToTop, updateOrAddRecentChat } from '../../../store/reducers/RecentChatsSlice';
import utils from '../../../utils';
import ChatFooter from './ChatFooter';

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
	const { displayName, active, groupActive } = currentChatPreview || {};

	const updateRecentChat = (msg) => {

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

	const getChatPreview = (chatId, successCallback) => {

		const isFirstLoad = true; // since chatId changed
		const pivot = isFirstLoad ? null : pivotMessageId;

		const { chatPreviewApi, retrieveConversationApi } = activeChatTab || {};

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

				successCallback({ chatId });	// read all messages

			}).catch(({ message }) => {
				console.error(message);
				setRetrievingChat(false);
			});

		}).catch(({ message, statusCode }) => {
			setRetrievingChat(false);
			if (statusCode == 403) {
				setPermissionDenied(true);
			}
		});

	};

	useEffect(() => {

		dispatch(setChatId(chatId));
		if (!currentChatPreview) {
			return getChatPreview(chatId);
		}

		dispatch(setShowChatHeaderOptionsModal(false));
		dispatch(clearCurrentChatPreview());
		setPivotMessageId(null); // reset state — this takes effect after render
		setRetrievingChat(true);
		getChatPreview(chatId, readAllMessages);

	}, [chatId]);

	const showStartNewConversation = active && (groupActive != false);

	return (
		<div id='ConversationScreen' className='FCSB'>
			{permissionDenied ? <PermissionDenied />
				: currentChatPreview ? <>
					<ChatHeader />
					<ChatMessages showStartNewConversation={showStartNewConversation} retrievingChat={retrievingChat} messages={messages} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
					<ChatFooter active={active} groupActive={groupActive} currentTab={activeChatTab.name} displayName={displayName} clickSendMessageHandler={clickSendMessageHandler} />
				</> : null
			}
		</div>
	);
}

export default ConversationScreen