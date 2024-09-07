import React from 'react'
import './SearchUsers.css'

const SearchUsers = () => {
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
    return (
        <div className='FRCB w100' id='SearchUserQuickAction'>
            <i className='fas fa-bars navbar cursP' onClick={showLeftNavContainer}></i>
            <div className='searchBox'>
                <input type='text' placeholder='Search Users' className='w100' autoComplete='off' spellCheck='false' />
                <i className='fas fa-search'></i>
            </div>
            <i className='fas fa-plus quickActions cursP'></i>
        </div>
    )
}

export default SearchUsers