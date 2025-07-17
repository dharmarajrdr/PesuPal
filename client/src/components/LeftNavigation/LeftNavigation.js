import { useEffect, useState } from 'react'
import './LeftNavigation.css'
import Nav from './Nav'
import { useDispatch, useSelector } from 'react-redux';
import OrgList from '../Org/OrgList';

const LeftNavigation = ({ profile }) => {

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

    const { id, icon, image, title, route } = profile;

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