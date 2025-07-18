import { useEffect, useState } from "react";
import "./Popup.css"; // Styling is the same

const Popup = ({ message, type = "info", duration = 30000 }) => {

    const [show, setShow] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => setShow(false), duration);
        // return () => clearTimeout(timer);
    }, [duration]);

    const iconClass = {
        success: "fa fa-check-circle",
        error: "fa fa-exclamation-circle",
        info: "fa fa-info-circle",
    };

    return show && (
        <div className={`popup-container ${type}`}>
            <i className={`icon ${iconClass[type]}`} aria-hidden="true" />
            <span className="message">{message}</span>
        </div>
    );
};

export default Popup;
