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

    const [authenticated, setAuthenticated] = useState(false);
    const [isSubscriptionExpired, setIsSubscriptionExpired] = useState(false);

    return (
        <Provider store={store}>
            <div className="App FRCS">
                {isAuthPage ? <AuthenticationRoutes /> : <>
                    <AuthModal setIsSubscriptionExpired={setIsSubscriptionExpired} setAuthenticated={setAuthenticated} />
                    <CommonContainer />
                    {authenticated ?
                        <AuthenticatedRoutes isSubscriptionExpired={isSubscriptionExpired} inLobby={inLobby} />
                        : <InitialLoadLogoPage />
                    }
                </>}
            </div>
        </Provider>
    );
}

export default App;
