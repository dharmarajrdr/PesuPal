import './PageNotFound.css';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
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
