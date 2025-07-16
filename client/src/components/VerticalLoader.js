import { useSelector } from 'react-redux';
import './VerticalLoader.css'; // Assuming you have a CSS file for styling

const VerticalLoader = () => {

    const isLoading = useSelector((state) => state.VerticalLoader.isLoading);

    if (!isLoading) {
        return null; // Don't render anything if not loading
    }

    return (
        <div className="vertical-loader-overlay">
            <div className="top-progress-loader-bar"></div>
        </div>
    );
};

export default VerticalLoader;
