import { useEffect, useRef, useState } from "react";
import "./Popup.css";
import { useDispatch, useSelector } from "react-redux";
import { hidePopup } from "../store/reducers/PopupSlice";

const Popup = () => {
    const duration = 3000;
    const dispatch = useDispatch();
    const { message, type } = useSelector(state => state.popup) || {};

    const audioRef = useRef(null);
    const [hasInteracted, setHasInteracted] = useState(false);

    // Detect first user interaction
    useEffect(() => {
        const enableAudio = () => setHasInteracted(true);
        window.addEventListener("click", enableAudio, { once: true });
        window.addEventListener("keydown", enableAudio, { once: true });
        return () => {
            window.removeEventListener("click", enableAudio);
            window.removeEventListener("keydown", enableAudio);
        };
    }, []);

    useEffect(() => {
        if (!message) return;

        if (type === "error" && hasInteracted) {
            audioRef.current?.play().catch((err) => {
                console.warn("Audio play blocked:", err);
            });
        }

        const timer = setTimeout(() => {
            dispatch(hidePopup());
        }, duration);

        return () => clearTimeout(timer);
    }, [message, hasInteracted, type, dispatch]);

    const iconClass = {
        success: "fa fa-check-circle",
        error: "fa fa-exclamation-circle",
        info: "fa fa-info-circle",
    };

    return message && (
        <div className={`popup-container ${type}`}>
            <audio ref={audioRef} src="/audio/on-error.mp3" preload="auto" />
            <i className={`icon ${iconClass[type]}`} aria-hidden="true" />
            <span className="message">{message}</span>
        </div>
    );
};

export default Popup;
