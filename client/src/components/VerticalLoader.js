import { useSelector } from 'react-redux';
import './VerticalLoader.css'; // Assuming you have a CSS file for styling

const VerticalLoader = () => {

    const isLoading = useSelector((state) => state.VerticalLoader.isLoading);

    return isLoading ? (
        <div className='entire-screen-overlay' id='vertical-loader'>
            <div className="top-progress-loader-bar"></div>
        </div>
    ) : null;
};

export default VerticalLoader;
