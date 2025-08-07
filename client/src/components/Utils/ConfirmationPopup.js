import { useDispatch, useSelector } from 'react-redux';
import './ConfirmationPopup.css';
import { hideConfirmationPopup } from '../../store/reducers/ConfirmationPopupSlice';

const ConfirmationPopup = () => {

    const { popupData } = useSelector(state => state.confirmationPopup);
    const { message, options } = popupData || {};
    const dispatch = useDispatch();

    const onClose = () => {
        dispatch(hideConfirmationPopup());
    }

    const handleClick = (customClickHandler) => {
        if (typeof customClickHandler === 'function') {
            customClickHandler();
        }
        onClose(); // close the popup after handling
    };

    return (message && options) ? (
        <div className="confirmation-overlay">
            <div className="confirmation-popup">
                <p className="confirmation-message">{message}</p>
                <div className="confirmation-buttons">
                    {options?.map(({ title, color, onClick }, index) => (
                        <button key={index} style={{ backgroundColor: color }} className="confirmation-button" onClick={() => handleClick(onClick)}> {title} </button>
                    ))}
                </div>
            </div>
        </div>
    ) : null;
};

export default ConfirmationPopup;
