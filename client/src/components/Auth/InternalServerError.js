import './PageNotFound.css';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { showLeftNavigation } from '../../store/reducers/LeftNavigationSlice';

const InternalServerError = ({ message = "Something went wrong. Please try again later." }) => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(showLeftNavigation(false)); // Hide left navigation on 500
    }, []);

    return (
        <div className="notfound-container w100 h100">
            <h1>500</h1>
            <p>{message}</p>
            <button onClick={() => navigate('/')}>Go to Home</button>
        </div>
    );
};

export default InternalServerError;
