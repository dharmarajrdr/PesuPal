import { useEffect } from "react";

export function UsePopupFromSession(showPopup) {

    useEffect(() => {
        const message = sessionStorage.getItem("popup-message");
        const type = sessionStorage.getItem("popup-type");

        if (message && type) {
            showPopup(message, type);
            sessionStorage.removeItem("popup-message");
            sessionStorage.removeItem("popup-type");
        }
    }, [showPopup]);
}
