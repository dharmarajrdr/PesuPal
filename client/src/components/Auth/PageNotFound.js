import { useNavigate } from 'react-router-dom';
import './PageNotFound.css';

const PageNotFound = () => {
    const navigate = useNavigate();

    return (
        <div className="notfound-container w100 h100">
            <h1>404</h1>
            <p>The page you're looking for doesn't exist.</p>
            <button onClick={() => navigate('/feeds')}>Go to Home</button>
        </div>
    );
};

export default PageNotFound;
