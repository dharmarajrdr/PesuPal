import './App.css';
import { useEffect } from 'react';
import Signup from './components/Auth/Signup';
import Signin from './components/Auth/Signin';
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import { hasCookie } from './components/Auth/utils';
import LeftNavigation from './components/LeftNavigation/LeftNavigation';
import FeedsLayout from './components/Feeds/FeedsLayout';
import ChatLayout from './components/Chat/ChatLayout';
import { configureStore } from '@reduxjs/toolkit';
import { combineReducers } from 'redux';
import { Provider } from 'react-redux';
import { NavigationReducers } from './store/reducers/Navigation';
import PeopleLayout from './components/People/PeopleLayout';
import TeamLayout from './components/Team/TeamLayout';

function Navigation() {
    const navigate = useNavigate();

    useEffect(() => {
        !hasCookie() && navigate('/signin');
    }, []);

    return null;
}

function App() {
    const store = configureStore({
        'reducer': combineReducers({
            Navigation: NavigationReducers
        }),
        'devTools': true
    });
    return (
        <Provider store={store}>
            <div className="App FRCS">
                <BrowserRouter>
                    {/* <Navigation /> */}
                    <LeftNavigation />
                    <Routes>
                        <Route path='/feeds' element={<FeedsLayout />} />
                        <Route path='/chat' element={<ChatLayout />} />
                        <Route path='/people' element={<PeopleLayout />} />
                        <Route path='/team/*' element={<TeamLayout />} />
                        <Route path='/signup' element={<Signup />} />
                        <Route path='/signin' element={<Signin />} />
                    </Routes>
                </BrowserRouter>
            </div>
        </Provider>
    );
}

export default App;