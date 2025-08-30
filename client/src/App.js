import './App.css';
import store from './store';
import { Provider } from 'react-redux';
import { useEffect, useState } from 'react';
import { hasCookie } from './components/Auth/utils';
import AuthModal from './components/Auth/AuthModal';
import AuthenticatedRoutes from './AuthenticatedRoutes';
import InitialLoadLogoPage from './InitialLoadLogoPage';
import AuthenticationRoutes from './AuthenticationRoutes';
import CommonContainer from './components/CommonContainer';
import { useLocation, useNavigate } from 'react-router-dom';
import InternalServerError from './components/Auth/InternalServerError';

function App() {

    const location = useLocation();
    const navigate = useNavigate();

    const inLobby = ['/', '/org/create'].includes(location.pathname);
    const isAuthPage = ['/signin', '/signup'].includes(location.pathname);

    useEffect(() => {
        if (!hasCookie() && !isAuthPage) {
            navigate('/signin');
        }
    }, [location.pathname, navigate]);

    const [serverDown, setServerDown] = useState(false);
    const [authenticated, setAuthenticated] = useState(false);
    const [isSubscriptionExpired, setIsSubscriptionExpired] = useState(false);

    return (
        <Provider store={store}>
            <div className="App FRCS">
                {isAuthPage ? <AuthenticationRoutes /> : <>
                    <AuthModal setServerDown={setServerDown} setIsSubscriptionExpired={setIsSubscriptionExpired} setAuthenticated={setAuthenticated} />
                    {authenticated ?
                        (serverDown ? <InternalServerError message='Server is down. Come back after having some coffee.' /> :
                            <AuthenticatedRoutes isSubscriptionExpired={isSubscriptionExpired} inLobby={inLobby} />
                        ) : <InitialLoadLogoPage />
                    }
                </>}
                <CommonContainer />
            </div>
        </Provider>
    );
}

export default App;
