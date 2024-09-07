import React from 'react'
import './LeftNavigation.css'
import ListOfNavigations from './ListOfNavigations'
import Nav from './Nav'

const LeftNavigation = () => {
    const hideNavContainer = () => {
        const LeftNavigationOverlay = document.getElementById('LeftNavigationOverlay'),
            leftNavContainer = document.getElementById('LeftNavigation');
        leftNavContainer.style.transition = 'transform 0.25s ease-in-out';
        leftNavContainer.style.transform = 'translateX(-100%)';
        const timer = setTimeout(() => {
            LeftNavigationOverlay.style.display = 'none';
            clearTimeout(timer);
        }, 100);
    }

    return (
        <div id='LeftNavigationOverlay'>
            <div id='LeftNavigation' className='FCCB'>
                <div className='w100'>
                    <div>
                        <i class="fa-solid fa-angles-left" id='closeLeftNav' onClick={hideNavContainer}></i>
                    </div>
                    {ListOfNavigations.top.map((navigation, index) => {
                        return (
                            <Nav key={index} icon={navigation.icon} title={navigation.title} route={navigation.route} />
                        )
                    })}
                </div>
                <div className='w100'>
                    {ListOfNavigations.bottom.map((navigation, index) => {
                        return (
                            <Nav key={index} icon={navigation.icon} title={navigation.title} route={navigation.route} />
                        )
                    })}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation