import './App.css';
import { useEffect } from 'react';
import Signup from './components/Auth/Signup';
import Signin from './components/Auth/Signin';
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import { hasCookie } from './components/Auth/utils';
import LeftNavigation from './components/LeftNavigation/LeftNavigation';
import FeedsLayout from './components/Feeds/FeedsLayout';
import ChatLayout from './components/Chat/ChatLayout';

function Navigation() {
    const navigate = useNavigate();

    useEffect(() => {
        !hasCookie() && navigate('/signin');
    }, []);

    return null;
}

function App() {
    return (
        <div className="App FRCC">
            <BrowserRouter>
                {/* <Navigation /> */}
                <LeftNavigation />
                <Routes>
                    <Route path='/feeds' element={<FeedsLayout />} />
                    <Route path='/chat' element={<ChatLayout />} />
                    <Route path='/signup' element={<Signup />} />
                    <Route path='/signin' element={<Signin />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;