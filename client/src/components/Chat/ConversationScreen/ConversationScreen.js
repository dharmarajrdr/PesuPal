import { useEffect, useRef, useState } from 'react'
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
import PageNotFound from '../../Auth/PageNotFound';
import { addMessage, setMessages } from '../../../store/reducers/ConversationSlice';
import { useWebSocketContext } from '../../../WebSocketProvider';

const ConversationScreen = ({ activeTabName }) => {

	const audioRef = useRef(null);
	const { chatId } = useParams();
	const dispatch = useDispatch();

	dispatch(setActiveChatTab(activeTabName));

	const playNotificationSound = () => {
		if (audioRef.current) {
			audioRef.current.currentTime = 0;
			audioRef.current.play();
		}
	}

	const [page, setPage] = useState(0);
	const [size, setSize] = useState(25);
	const [permissionDenied, setPermissionDenied] = useState(false);
	const [chatNotFound, setChatNotFound] = useState(false);
	const [retrievingChat, setRetrievingChat] = useState(true);
	const [pivotMessageId, setPivotMessageId] = useState(null);

	const myProfile = useSelector(state => state.myProfile) || {};
	const activeChatTab = useSelector(state => state.activeChatTab);
	const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
	const { displayName, active, groupActive, groupChatConfiguration } = currentChatPreview || {};
	const { postMessage: messagePostable, deleteMessage: messageDeletable, editMessage: messageEditable } = groupChatConfiguration || {};

	const { DirectMessage, GroupMessage } = useWebSocketContext();

	const readAllMessages = ({ chatId, chatPreview }) => {

		const { readAllMessagesApi } = activeChatTab || {};
		const { active } = chatPreview || {};

		if (!active) {
			return;
		}

		apiRequest(`${readAllMessagesApi}/${chatId}/read-all`, "PUT").then(() => {

		}).catch(({ message }) => {
			dispatch(showPopup({ message, type: 'error' }));
		});
	}

	const clickSendMessageHandler = ({ message, media }) => {

		const payload = {
			message, chatId, media
		};

		if (activeChatTab.name === 'directMessage') {
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

			const chatPreview = data || {};

			setPermissionDenied(false);
			setChatNotFound(false);
			dispatch(setCurrentChatPreview(data));

			apiRequest(`${retrieveConversationApi}/${chatId}?page=${page}&size=${size}${pivot ? `&pivot_message_id=${pivot}` : ''}`, "GET").then(({ data }) => {
				dispatch(setMessages(data));
				setRetrievingChat(false);

				// Update pivot to the last message’s ID
				if (data.length > 0) {
					setPivotMessageId(data.at(-1)?.id);
				}

				successCallback && successCallback({ chatId, chatPreview });	// read all messages

			}).catch(({ message }) => {
				console.error(message);
				setRetrievingChat(false);
			});

		}).catch(({ message, statusCode }) => {
			setRetrievingChat(false);
			if (statusCode == 403) {
				setPermissionDenied(true);
			} else if (statusCode == 404) {
				setChatNotFound(true);
			}
		});

	};

	useEffect(() => {

		dispatch(setChatId(chatId));
		if (!currentChatPreview) {
			return getChatPreview(chatId, readAllMessages);
		}

		dispatch(setShowChatHeaderOptionsModal(false));
		dispatch(clearCurrentChatPreview());
		setPivotMessageId(null); // reset state — this takes effect after render
		setRetrievingChat(true);
		getChatPreview(chatId, readAllMessages);

	}, [chatId]);

	const showStartNewConversation = active && (activeChatTab.chatMode === 'group' ? groupActive != false : true);

	return (
		<div id='ConversationScreen' className='FCSB'>
			{
				chatNotFound ? <PageNotFound /> :
					permissionDenied ? <PermissionDenied />
						: currentChatPreview ? <>
							<ChatHeader />
							<audio ref={audioRef} src="/audio/on-message.mp3" preload="auto" />
							<ChatMessages messageDeletable={messageDeletable} messageEditable={messageEditable} showStartNewConversation={showStartNewConversation} retrievingChat={retrievingChat} chatId={chatId} clickSendMessageHandler={clickSendMessageHandler} />
							<ChatFooter messagePostable={messagePostable} active={active} groupActive={groupActive} currentTab={activeChatTab.name} displayName={displayName} clickSendMessageHandler={clickSendMessageHandler} />
						</> : null
			}
		</div>
	);
}

export default ConversationScreen