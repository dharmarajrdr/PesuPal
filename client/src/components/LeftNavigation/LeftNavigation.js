import React from 'react'
import './LeftNavigation.css'
import ListOfNavigations from './ListOfNavigations'
import Nav from './Nav'
import themes from '../../theme';

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
    }, { LeftNavigationStyles } = themes;

    return (
        <div id='LeftNavigationOverlay'>
            <div id='LeftNavigation' className='FCCB' style={LeftNavigationStyles}>
                <div className='w100'>
                    <div id='app_logo'>
                        <img src='/logo512.png' />
                    </div>
                    <div>
                        <i className="fa-solid fa-angles-left" id='closeLeftNav' onClick={hideNavContainer}></i>
                    </div>
                    {ListOfNavigations.top.map((navigation, index) => {
                        return (
                            <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} />
                        )
                    })}
                </div>
                <div className='w100'>
                    {ListOfNavigations.bottom.map((navigation, index) => {
                        return (
                            <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} />
                        )
                    })}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation