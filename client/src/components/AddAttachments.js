import React, { useRef, useState } from "react";
import "./AddAttachments.css";
import { useDispatch } from "react-redux";
import { showPopup } from "../store/reducers/PopupSlice";

const fileTypeIcons = {
    image: "fa fa-image",
    video: "fa fa-video",
    audio: "fa fa-music",
    pdf: "fa fa-file-pdf",
    doc: "fa fa-file-word",
    docx: "fa fa-file-word",
    xls: "fa fa-file-excel",
    xlsx: "fa fa-file-excel",
    ppt: "fa fa-file-powerpoint",
    pptx: "fa fa-file-powerpoint",
    default: "fa fa-file"
};

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

    const getFileIcon = (file) => {
        if (file.file.type.startsWith("image")) return fileTypeIcons.image;
        if (file.file.type.startsWith("video")) return fileTypeIcons.video;
        if (file.file.type.startsWith("audio")) return fileTypeIcons.audio;
        if (file.file.type.includes("pdf")) return fileTypeIcons.pdf;
        if (
            file.file.type.includes("word") ||
            file.file.name.endsWith(".doc") ||
            file.file.name.endsWith(".docx")
        )
            return fileTypeIcons.doc;
        if (
            file.file.type.includes("excel") ||
            file.file.name.endsWith(".xls") ||
            file.file.name.endsWith(".xlsx")
        )
            return fileTypeIcons.xls;
        if (
            file.file.type.includes("powerpoint") ||
            file.file.name.endsWith(".ppt") ||
            file.file.name.endsWith(".pptx")
        )
            return fileTypeIcons.ppt;
        return fileTypeIcons.default;
    };

    return (
        <div className="attachments-container FCCS">
            <button className="add-attachment-btn" onClick={() => fileInputRef.current.click()}>
                <i className="fa fa-cloud-arrow-up mR5 w20 colorFFF" /> Add Attachments
            </button>

            <input
                type="file"
                multiple
                ref={fileInputRef}
                style={{ display: "none" }}
                onChange={handleFileChange}
                accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"}
            />

            {files.length > 0 &&
                <div className="attachments-list w100 FRCS mB10">
                    {files.map((fileObj, index) => (
                        <div key={index} className="attachment-item">
                            <div className="file-preview">
                                {fileObj.preview ? (
                                    <img
                                        src={fileObj.preview}
                                        alt={fileObj.file.name}
                                        className="file-thumbnail"
                                    />
                                ) : (
                                    <i className={`${getFileIcon(fileObj)} file-icon`} />
                                )}
                            </div>
                            <div className="file-info FCSS">
                                <span className="file-name mB5">{fileObj.file.name}</span>
                                <span className="file-size">
                                    {(fileObj.file.size / 1024).toFixed(2)} KB
                                </span>
                            </div>
                            <i className="fa fa-trash remove-btn" onClick={() => removeFile(index)} title="Remove file" />
                        </div>
                    ))}
                </div>
            }
        </div >
    );
}
