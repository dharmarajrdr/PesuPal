import Nav from './Nav';
import './LeftNavigation.css';
import OrgList from '../Org/OrgList';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../store/reducers/PopupSlice';
import { setMyProfile } from '../../store/reducers/MyProfileSlice';
import { hideOrgList, showOrgList } from '../../store/reducers/OrgSlice';

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

    const { showingOrgList } = useSelector((state) => state.org);
    const [orgId, setOrgId] = useState(sessionStorage.getItem('org-id'));
    const [profile, setProfile] = useState({ 'id': 8, 'title': 'Me', 'route': '/profile', 'icon': 'fa-regular fa-user', 'isActive': false });

    const { id, icon, image, title, route } = profile;

    useEffect(() => {
        apiRequest("/api/v1/people/profile", "GET").then(({ data }) => {
            dispatch(setMyProfile(data));
            if (data.displayPicture) {
                const updatedProfile = { ...profile, image: data.displayPicture, icon: null };
                setProfile(updatedProfile);
            }
        }).catch(({ message, statusCode }) => {
            if (statusCode == 403) {
                sessionStorage.removeItem('token');
                document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/';
                return window.location.href = '/signin';
            }
            dispatch(showPopup({ message, type: 'error' }));
            console.error("Error fetching profile:", message);
        });
    }, [orgId]);

    const toggleOrgList = (e) => {

        const clickedOrgList = e.currentTarget;

        if (clickedOrgList.classList.contains("org-preview")) {
            const isActive = clickedOrgList.classList.contains("active");
            if (!isActive) {
                dispatch(hideOrgList());
            }
        }
    };

    const showOrgListHandler = () => {

        dispatch(showOrgList());
    }

    const closeOrgList = () => {

        dispatch(hideOrgList());
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
                    {ListOfNavigations.top.map((navigation, index) => <Nav key={index} icon={navigation.icon} fontWeight={navigation.fontWeight} image={navigation.image} title={navigation.title} route={navigation.route} notifyCount={navigation.notifyCount} />)}
                </div>
                <div className='w100'>
                    <Nav key={id} icon={icon} image={image} title={title} route={route} />
                    {ListOfNavigations.bottom.map((navigation, index) => <Nav key={index} icon={navigation.icon} fontWeight={navigation.fontWeight} image={navigation.image} title={navigation.title} route={navigation.route} showOrgListHandler={showOrgListHandler} />)}
                    {showingOrgList && <OrgList toggleOrgList={toggleOrgList} closeOrgList={closeOrgList} />}
                </div>
            </div>
        </div>
    )
}

export default LeftNavigation