import { useNavigate } from 'react-router-dom';
import './PageNotFound.css';
import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { showLeftNavigation } from '../../store/reducers/LeftNavigationSlice';

const InternalServerError = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(showLeftNavigation(false)); // Hide left navigation on 500
    }, []);

    return (
        <div className="notfound-container w100 h100">
            <h1>500</h1>
            <p>Something went wrong. Please try again later.</p>
            <button onClick={() => navigate('/feeds')}>Go to Home</button>
        </div>
    );
};

export default InternalServerError;
