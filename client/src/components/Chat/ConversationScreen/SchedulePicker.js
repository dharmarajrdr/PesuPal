import { useEffect, useRef, useState } from "react";
import { useDispatch } from "react-redux";
import './SchedulePicker.css';
import { showPopup } from "../../../store/reducers/PopupSlice";

const getMinDateTime = () => {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset()); // fix UTC offset for datetime-local
    return now.toISOString().slice(0, 16);
};

const SchedulePicker = ({ onSchedule, onCancel, showPicker, setShowPicker }) => {

    const dispatch = useDispatch();
    const pickerRef = useRef(null);
    const schedulePickerRef = useRef(null);
    const [scheduledTime, setScheduledTime] = useState("");

    const handleScheduleClick = () => {
        if (scheduledTime) {
            onSchedule(scheduledTime);
        } else {
            dispatch(showPopup({ message: "Please select a date and time to schedule.", 'type': "error" }));
        }
    };

    useEffect(() => {
        if (showPicker && pickerRef.current) {
            const { y: schedulePickerY } = schedulePickerRef.current.getBoundingClientRect();
            if (schedulePickerY < 0) {
                schedulePickerRef.current.style.top = `90px`;
            }
        }
    }, [showPicker]);

    return showPicker && (

        <div id="schedule-picker-container" className="FCCC" ref={schedulePickerRef}>

            <input type="datetime-local" onChange={(e) => setScheduledTime(e.target.value)} ref={pickerRef} min={getMinDateTime()} value={scheduledTime} />

            <div className="FRCC mT10 w100">
                <button id="schedule-button" onClick={handleScheduleClick}>Schedule</button>
                <button id="cancel-button" className="mL10" onClick={() => { setShowPicker(false); onCancel && onCancel(); }}>Cancel</button>
            </div>

        </div>
    );
};

export default SchedulePicker;