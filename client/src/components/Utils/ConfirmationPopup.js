import './ConfirmationPopup.css';

const ConfirmationPopup = ({ message, options, onClose }) => {
    const handleClick = (customClickHandler) => {
        if (typeof customClickHandler === 'function') {
            customClickHandler();
        }
        onClose(); // close the popup after handling
    };

    return (
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
    );
};

export default ConfirmationPopup;
