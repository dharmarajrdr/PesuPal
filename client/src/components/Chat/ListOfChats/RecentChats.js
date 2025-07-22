import { useEffect, useState } from 'react'
import './RecentChats.css'
import RecentChat from './RecentChat'
import { apiRequest } from '../../../http_request'
import Loader from '../../Loader'
import ErrorMessage from '../../ErrorMessage'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { setActiveRecentChat } from '../../../store/reducers/ActiveRecentChatSlice'
import { setRecentChats } from '../../../store/reducers/RecentChatsSlice'

const NoChatsFound = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-comments mR5' aria-hidden='true' />
                No chats found
            </p>
            <p className='w100 alignCenter'>Create a new chat to get started.</p>
        </div>
    )
}

const RecentChats = () => {

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const recentChats = useSelector(state => state.recentChats);

    useEffect(() => {
        apiRequest(`/api/v1/direct-messages/recent?page=${page}&size=${size}`, 'GET').then(({ data }) => {
            dispatch(setRecentChats(data));
            setLoading(false);
        }).catch(({ message }) => {
            setError(message);
            setLoading(false);
        });
    }, []);

    const openChatHandler = (chat) => {
        navigate(`/chat/messages/${chat.chatId}`);
        dispatch(setActiveRecentChat(chat));
    }

    return (
        <div id='RecentChats' className='FCCS w100'>
            {
                loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> :
                        recentChats?.length ?
                            recentChats.map((recentChat, index) =>
                                <RecentChat key={index} recentChat={recentChat} openChatHandler={openChatHandler} />
                            ) : <NoChatsFound />
            }
        </div>
    )
}

export default RecentChats