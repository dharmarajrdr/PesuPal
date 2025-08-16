import './SearchUsers.css'
import { useEffect, useRef, useState } from 'react';
import OptionsModal from '../../Utils/OptionsModal';
import CreateGroupModal from '../Group/CreateGroupModal';
import { useDispatch, useSelector } from 'react-redux';
import { apiRequest } from '../../../http_request';
import { setRecentChats } from '../../../store/reducers/RecentChatsSlice';

const SearchUsers = ({ searchChat, setSearchChat }) => {

    const isFirstRender = useRef(true);

    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const activeChatTab = useSelector(state => state.activeChatTab);
    const [showOptionsModal, setShowOptionsModal] = useState(false);
    const [showCreateGroupModal, setShowCreateGroupModal] = useState(false);

    const { recentChatsApi, chatMode } = activeChatTab;

    const showLeftNavContainer = () => {
        const LeftNavigationOverlay = document.getElementById('LeftNavigationOverlay'),
            leftNavContainer = document.getElementById('LeftNavigation');
        LeftNavigationOverlay.style.display = 'block';
        const timer = setTimeout(() => {
            leftNavContainer.style.transition = 'transform 0.25s ease-in';
            leftNavContainer.style.transform = 'translateX(100%)';
            clearTimeout(timer);
        }, 100);
    }

    const options = [{
        "icon": "fa fa-users",
        "name": "Create Group",
        "onClick": () => {
            setShowCreateGroupModal(true);
            setShowOptionsModal(false);
        }
    }];

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false;
            return;
        }
        const handler = setTimeout(() => {
            apiRequest(`${recentChatsApi}?search=${searchChat}&page=${page}&size=${size}`, 'GET').then(({ data }) => {
                dispatch(setRecentChats(data));
            }).catch(({ message }) => {
                dispatch(setRecentChats([]));
            });
        }, 300);

        return () => clearTimeout(handler);
    }, [searchChat]);

    return (
        <div className='FRCB w100' id='SearchUserQuickAction'>
            <i className='fas fa-bars navbar cursP' onClick={showLeftNavContainer}></i>
            <div className='searchBox'>
                <input type='text' placeholder={`Search ${chatMode === 'DIRECT_MESSAGE' ? 'Users' : 'Groups'}`} className='w100' autoComplete='off' spellCheck='false' value={searchChat} onChange={(e) => setSearchChat(e.target.value)} />
                <i className='fas fa-search'></i>
            </div>

            {showCreateGroupModal && <CreateGroupModal setShowCreateGroupModal={setShowCreateGroupModal} />}
            {showOptionsModal && <OptionsModal options={options} style={{ right: '-140px', top: '10px' }} />}
            <i className='fas fa-plus quickActions cursP' onClick={() => setShowOptionsModal(!showOptionsModal)}></i>
        </div>
    )
}

export default SearchUsers