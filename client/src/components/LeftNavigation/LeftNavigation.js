import { useEffect, useState } from 'react'
import './LeftNavigation.css'
import Nav from './Nav'
import { useDispatch, useSelector } from 'react-redux';
import OrgList from '../Org/OrgList';
import { setMyProfile } from '../../store/reducers/MyProfileSlice';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';

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
    }, dispatch = useDispatch();

    const [showOrgList, setShowOrgList] = useState(false);
    const [orgId, setOrgId] = useState(sessionStorage.getItem('org-id'));
    const [profile, setProfile] = useState({ 'id': 8, 'title': 'Me', 'route': '/profile', 'icon': 'fa-regular fa-user', 'isActive': false });

    const { id, icon, image, title, route } = profile;

    useEffect(() => {
        apiRequest("/api/v1/people/profile", "GET").then(({ data }) => {
            dispatch(setMyProfile(data));
            const updatedProfile = { ...profile, image: data.displayPicture, icon: null };
            setProfile(updatedProfile);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            console.error("Error fetching profile:", message);
        });
    }, [orgId]);

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

    useEffect(() => {
        const { pathname } = document.location;
        const route = '/' + pathname.split('/')[1];
        dispatch({ type: 'UPDATE_NAVIGATION', payload: { route } });
    }, []);

    return (
        <div id='LeftNavigationOverlay' onClick={clickedOverlay}>
            <div id='LeftNavigation' className='FCCB'>
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
                    <Nav key={id} icon={icon} image={image} title={title} route={route} />
                    {ListOfNavigations.bottom.map((navigation, index) => <Nav key={index} icon={navigation.icon} image={navigation.image} title={navigation.title} route={navigation.route} showOrgListHandler={showOrgListHandler} />)}
                    {showOrgList && <OrgList toggleOrgList={toggleOrgList} closeOrgList={closeOrgList} />}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation