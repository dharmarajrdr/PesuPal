import React, { useEffect } from 'react'
import './LeftNavigation.css'
import Nav from './Nav'
import { useDispatch, useSelector } from 'react-redux';
import themes from '../../theme';

const LeftNavigation = () => {

    const ListOfNavigations = useSelector((state) => {
        return state.Navigation;
    }), hideNavContainer = () => {
        const LeftNavigationOverlay = document.getElementById('LeftNavigationOverlay'),
            leftNavContainer = document.getElementById('LeftNavigation');
        leftNavContainer.style.transition = 'transform 0.25s ease-in-out';
        leftNavContainer.style.transform = 'translateX(-100%)';
        const timer = setTimeout(() => {
            LeftNavigationOverlay.style.display = 'none';
            clearTimeout(timer);
        }, 100);
    }, clickedOverlay = () => {
        if (window.outerWidth < 769) {
            hideNavContainer();
        }
    }, { LeftNavigationStyles } = themes,
        dispatch = useDispatch();

    useEffect(() => {
        const { pathname } = document.location;
        const route = '/' + pathname.split('/')[1];
        dispatch({ type: 'UPDATE_NAVIGATION', payload: { route } });
    }, []);

    return (
        <div id='LeftNavigationOverlay' onClick={clickedOverlay}>
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
                            <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} isActive={navigation.isActive} />
                        )
                    })}
                </div>
                <div className='w100'>
                    {ListOfNavigations.bottom.map((navigation, index) => {
                        return (
                            <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} isActive={navigation.isActive} />
                        )
                    })}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation