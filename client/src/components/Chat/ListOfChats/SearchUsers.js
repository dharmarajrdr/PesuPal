import './SearchUsers.css'
import { useState } from 'react';
import OptionsModal from '../../Utils/OptionsModal';
import CreateGroupModal from '../Group/CreateGroupModal';

const SearchUsers = () => {

    const [showOptionsModal, setShowOptionsModal] = useState(false);
    const [showCreateGroupModal, setShowCreateGroupModal] = useState(false);

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

    return (
        <div className='FRCB w100' id='SearchUserQuickAction'>
            <i className='fas fa-bars navbar cursP' onClick={showLeftNavContainer}></i>
            <div className='searchBox'>
                <input type='text' placeholder='Search Users' className='w100' autoComplete='off' spellCheck='false' />
                <i className='fas fa-search'></i>
            </div>

            {showCreateGroupModal && <CreateGroupModal setShowCreateGroupModal={setShowCreateGroupModal} />}
            {showOptionsModal && <OptionsModal options={options} style={{ right: '-140px', top: '10px' }} />}
            <i className='fas fa-plus quickActions cursP' onClick={() => setShowOptionsModal(!showOptionsModal)}></i>
        </div>
    )
}

export default SearchUsers