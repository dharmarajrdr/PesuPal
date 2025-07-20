import { useEffect, useState } from 'react'
import './RecentChats.css'
import RecentChat from './RecentChat'
import { apiRequest } from '../../../http_request'
import Loader from '../../Loader'
import ErrorMessage from '../../ErrorMessage'
import { useNavigate } from 'react-router-dom'

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


const RecentChats = ({ activeRecentChat }) => {

    const [recentChat, setRecentChat] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        apiRequest('/api/v1/direct-messages/recent?page=0&size=10', 'GET').then(({ data }) => {
            setRecentChat(data);
            setLoading(false);
        }).catch(({ message }) => {
            setError(message);
            setLoading(false);
        });
    }, []);

    const openChatHandler = (chatId) => {
        navigate(`/chat/${chatId}`);
        activeRecentChat[1](chatId);
    }

    return (
        <div id='RecentChats' className='FCCS w100'>
            {
                loading ? <Loader /> :
                    error ? <ErrorMessage message={error} /> :
                        recentChat.length ?
                            recentChat.map((recentChat, index) =>
                                <RecentChat key={index} recentChat={recentChat} activeRecentChat={activeRecentChat[0]} openChatHandler={openChatHandler} />
                            ) : <NoChatsFound />
            }
        </div>
    )
}

export default RecentChats