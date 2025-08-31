import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import './ConversationScreen.css'
import ChatHeader from "./ChatHeader";
import ChatMessages from "./ChatMessages";
import ChatFooter from "./ChatFooter";
import useChatWebSocket from "../../../useChatWebSocket";
import { apiRequest } from "../../../http_request";
import { setMessages } from "../../../store/reducers/ConversationSlice";
import { setChatId } from "../../../store/reducers/ChatIdSlice";
import { setActiveChatTab } from "../../../store/reducers/ActiveChatTabSlice";
import { clearCurrentChatPreview, setCurrentChatPreview } from "../../../store/reducers/CurrentChatPreviewSlice";
import PageNotFound from "../../Auth/PageNotFound";
import PermissionDenied from "../../Auth/PermissionDenied";

const ConversationScreen = ({ activeTabName }) => {

	console.log({ activeTabName });

	const { chatId } = useParams();
	const dispatch = useDispatch();

	const audioRef = useRef(null);
	const [page, setPage] = useState(0);
	const [size, setSize] = useState(25);
	const [permissionDenied, setPermissionDenied] = useState(false);
	const [chatNotFound, setChatNotFound] = useState(false);
	const [retrievingChat, setRetrievingChat] = useState(true);
	const [pivotMessageId, setPivotMessageId] = useState(null);

	const activeChatTab = useSelector((state) => state.activeChatTab);
	const currentChatPreview = useSelector((state) => state.currentChatPreviewSlice);

	const playNotificationSound = () => {
		if (audioRef.current) {
			audioRef.current.currentTime = 0;
			audioRef.current.play();
		}
	};

	const { DirectMessage, GroupMessage } = useChatWebSocket({
		onMessageSound: playNotificationSound,
	});

	const clickSendMessageHandler = ({ message, media }) => {
		const payload = { message, chatId, media };
		if (activeChatTab.name === "directMessage") {
			DirectMessage.send(payload);
		} else if (activeChatTab.name === "groupMessage") {
			GroupMessage.send(payload);
		}
	};

	const getChatPreview = (chatId, successCallback) => {

		console.log({ chatId, activeChatTab });

		const isFirstLoad = true;
		const pivot = isFirstLoad ? null : pivotMessageId;
		const { chatPreviewApi, retrieveConversationApi } = activeChatTab || {};

		if (!chatPreviewApi) return;

		apiRequest(`${chatPreviewApi}/${chatId}`, "GET")
			.then(({ data }) => {
				dispatch(setCurrentChatPreview(data));
				setPermissionDenied(false);
				setChatNotFound(false);

				return apiRequest(
					`${retrieveConversationApi}/${chatId}?page=${page}&size=${size}${pivot ? `&pivot_message_id=${pivot}` : ""}`, "GET"
				);
			})
			.then(({ data }) => {
				dispatch(setMessages(data));
				setRetrievingChat(false);
				if (data.length > 0) setPivotMessageId(data.at(-1)?.id);
				successCallback && successCallback({ chatId });
			})
			.catch(({ message, statusCode }) => {
				setRetrievingChat(false);
				if (statusCode == 403) setPermissionDenied(true);
				else if (statusCode == 404) setChatNotFound(true);
				else console.error(message);
			});
	};

	useEffect(() => {
		console.log({ chatId, activeTabName });
		dispatch(setChatId(chatId));
		dispatch(setActiveChatTab(activeTabName));
		dispatch(clearCurrentChatPreview());
		setPivotMessageId(null);
		setRetrievingChat(true);
		getChatPreview(chatId);
	}, [chatId]);

	const { displayName, active, groupActive, groupChatConfiguration } = currentChatPreview || {};
	const { postMessage, deleteMessage, editMessage } = groupChatConfiguration || {};

	const showStartNewConversation = active && (activeChatTab.chatMode === "group" ? groupActive !== false : true);

	return (
		<div id="ConversationScreen" className="FCSB">
			{chatNotFound ? (
				<PageNotFound />
			) : permissionDenied ? (
				<PermissionDenied />
			) : currentChatPreview ? (
				<>
					<ChatHeader />
					<audio ref={audioRef} src="/audio/on-message.mp3" preload="auto" />
					<ChatMessages
						messageDeletable={deleteMessage}
						messageEditable={editMessage}
						showStartNewConversation={showStartNewConversation}
						retrievingChat={retrievingChat}
						chatId={chatId}
						clickSendMessageHandler={clickSendMessageHandler}
					/>
					<ChatFooter
						messagePostable={postMessage}
						active={active}
						groupActive={groupActive}
						currentTab={activeChatTab.name}
						displayName={displayName}
						clickSendMessageHandler={clickSendMessageHandler}
					/>
				</>
			) : null}
		</div>
	);
};

export default ConversationScreen;
