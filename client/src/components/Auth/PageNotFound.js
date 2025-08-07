import { useNavigate } from 'react-router-dom';
import './PageNotFound.css';
import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { showLeftNavigation } from '../../store/reducers/LeftNavigationSlice';

const PageNotFound = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(showLeftNavigation(false)); // Hide left navigation on 404
    }, []);

    return (
        <div className="notfound-container w100 h100">
            <h1>404</h1>
            <p>The page you're looking for doesn't exist.</p>
            <button onClick={() => navigate('/feeds')}>Go to Home</button>
        </div>
    );
};

export default PageNotFound;
