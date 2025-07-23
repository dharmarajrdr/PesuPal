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
import { moveRecentChatToTop, updateRecentChat } from '../../../store/reducers/RecentChatsSlice';
import utils from '../../../utils';

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

	const { DirectMessage, GroupMessage } = useWebSocket({
		userId: myProfile.id,
		onPrivateMessage: (msg) => {
			const { chatMode, message } = msg || {};

			if (chatMode !== activeChatTab.chatMode) {
				return; // If the message is not in the current chat mode, ignore it
			}

			if (activeChatTab.name == 'directMessage') {
				const isChatOpen = chatId == msg.chatId;
				if (isChatOpen) {
					setMessages((prev) => [...prev, msg]);
				}
				const recentMessage = {
					createdAt: utils.convertTime(new Date(), 12),
					media: false,
					message,
					// sender: "Me",	// Send by them, so no need to set sender
					readReceipt: "SENT"
				};
				if (!isChatOpen) {  // If the chat is not open, then show the number of unread messages
					Object.assign(recentMessage, { number_of_unread_messages: 1 });
				}
				dispatch(updateRecentChat({ chatId: msg.chatId, recentMessage }));
				dispatch(moveRecentChatToTop(msg.chatId));
			} else if (activeChatTab.name == 'groupMessage') {

			}
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

		}).catch(({ message }) => {
			dispatch(showPopup({ message, type: 'error' }));
		});
	}

	const clickSendMessageHandler = ({ message }) => {

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

		if (activeChatTab.name === 'directMessage') {
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
				<ChatMessages retrievingChat={retrievingChat} messages={messages} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
				{active ? <ChatInput clickSendMessageHandler={clickSendMessageHandler} /> : <ChatInputUserArchived displayName={displayName} />}
			</>}
		</div>
	) : null;
}

export default ConversationScreen