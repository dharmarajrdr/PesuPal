import { useNavigate } from 'react-router-dom';
import './PageNotFound.css';

const PermissionDenied = () => {

    const navigate = useNavigate();

    return (
        <div className="notfound-container w100 h100">
            <h1>403</h1>
            <p>You do not have permission to access this page.</p>
            <button onClick={() => navigate('/feeds')}>Go to Home</button>
        </div>
    );
}

export default PermissionDenied