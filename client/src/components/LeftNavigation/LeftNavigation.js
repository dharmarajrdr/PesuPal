import React, { useEffect, useState } from 'react'
import './LeftNavigation.css'
import Nav from './Nav'
import { useDispatch, useSelector } from 'react-redux';
import themes from '../../theme';
import OrgList from '../Org/OrgList';
import { apiRequest } from '../../http_request';

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

    const [showOrgList, setShowOrgList] = useState(false);

    const toggleOrgList = (e) => {

        const clickedOrgList = e.currentTarget;

        if (clickedOrgList.classList.contains("org-preview")) {
            const isActive = clickedOrgList.classList.contains("active");
            if (!isActive) {
                setShowOrgList(false);
            }
        }
    };

    const showOrgListHandler = () => {

        setShowOrgList(true);
    }

    const closeOrgList = () => {

        setShowOrgList(false);
    }

    const [profile, setProfile] = useState({
        'id': 8,
        'title': 'Me',
        'route': '/profile',
        'icon': 'fa-regular fa-user',
        'isActive': false
    });

    useEffect(() => {
        const { pathname } = document.location;
        const route = '/' + pathname.split('/')[1];

        apiRequest("/api/v1/people/display-picture", "GET").then(({ data }) => {
            Object.assign(profile, { 'image': data, 'icon': null });
            setProfile(profile);
        }).catch(({ message }) => {
            console.error("Error fetching profile image:", message);
        });

        dispatch({ type: 'UPDATE_NAVIGATION', payload: { route } });
    }, [profile]);

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
                    {ListOfNavigations.top.map((navigation, index) => <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} notifyCount={navigation.notifyCount} />)}
                </div>
                <div className='w100'>
                    <Nav key={profile.id} icon={profile.icon} image={profile.image} title={profile.title} route={profile.route} />
                    {ListOfNavigations.bottom.map((navigation, index) => <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} showOrgListHandler={showOrgListHandler} />)}
                    {showOrgList && <OrgList toggleOrgList={toggleOrgList} closeOrgList={closeOrgList} />}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation