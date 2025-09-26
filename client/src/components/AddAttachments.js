import { useRef } from "react";
import "./AddAttachments.css";
import { useDispatch } from "react-redux";
import { showPopup } from "../store/reducers/PopupSlice";
import AttachmentItem from "./Chat/AttachmentItem";

export default function AddAttachments({ allowedTypes = [], maxFileSize, maxFiles, files, setFiles }) {


    const fileInputRef = useRef(null);
    const dispatch = useDispatch();

    if (files === undefined || setFiles === undefined) {
        return dispatch(showPopup({ message: "Files and setFiles props are required.", type: 'error' }));
    }

    const handleFileChange = (e) => {

        const selectedFiles = Array.from(e.target.files);

        if (maxFiles && selectedFiles.length + files.length > maxFiles) {
            return dispatch(showPopup({ message: `You can only upload a maximum of ${maxFiles} files.`, type: 'error' }));
        }

        const filtered = allowedTypes.length
            ? selectedFiles.filter((file) =>
                allowedTypes.some((type) => file.type.includes(type))
            )
            : selectedFiles;

        if (filtered.length < selectedFiles.length) {
            return dispatch(showPopup({ message: "Some files were not allowed based on file type restrictions.", type: 'error' }));
        }

        if (maxFileSize && filtered.some((file) => file.size > maxFileSize)) {
            return dispatch(showPopup({ message: `File size exceeds the maximum limit of ${maxFileSize / 1024 / 1024} MB.`, type: 'error' }));
        }

        const withPreview = filtered.map((file) => ({
            file,
            preview: file.type.startsWith("image")
                ? URL.createObjectURL(file)
                : null
        }));

        setFiles((prev) => [...prev, ...withPreview]);
        e.target.value = ""; // Reset file input
    };

    const removeFile = (index) => {
        setFiles((prev) => {
            const updated = [...prev];
            updated.splice(index, 1);
            return updated;
        });
    };

    return (
        <div className="attachments-container FCCS">

            <button className="add-attachment-btn" onClick={() => fileInputRef.current.click()}>
                <i className="fa fa-cloud-arrow-up mR5 w20 colorFFF" /> Add Attachments
            </button>

            <input type="file" multiple ref={fileInputRef} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} style={{ display: "none" }} />

            {files.length > 0 ? (
                <div className="attachments-list w100 FRCS mB10">
                    {files.map((file, index) => (
                        <AttachmentItem key={index} file={file} index={index} removeFile={removeFile} />
                    ))}
                </div>
            ) : null}
        </div >
    );
}
