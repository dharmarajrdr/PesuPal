import './App.css';
import { useEffect } from 'react';
import Signup from './components/Auth/Signup';
import Signin from './components/Auth/Signin';
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import { hasCookie } from './components/Auth/utils';
import Home from './components/Home/Home';

function Navigation() {
    const navigate = useNavigate();

    useEffect(() => {
        !hasCookie() && navigate('/signin');
    }, []);

    return null;
}

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Navigation />
                <Routes>
                    <Route path='/home' element={<Home />} />
                    <Route path='/signup' element={<Signup />} />
                    <Route path='/signin' element={<Signin />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;