import React, { useRef, useState } from "react";
import "./AddAttachments.css";

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

export default function AddAttachments({ allowedTypes = [] }) {

    const [files, setFiles] = useState([]);
    const fileInputRef = useRef(null);

    const handleFileChange = (e) => {
        const selectedFiles = Array.from(e.target.files);
        const filtered = allowedTypes.length
            ? selectedFiles.filter((file) =>
                allowedTypes.some((type) => file.type.includes(type))
            )
            : selectedFiles;

        if (filtered.length < selectedFiles.length) {
            alert("Some files were not allowed based on file type restrictions.");
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
        <div className="attachments-container">
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
                <div className="attachments-list">
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
                            <div className="file-info">
                                <span className="file-name">{fileObj.file.name}</span>
                                <span className="file-size">
                                    {(fileObj.file.size / 1024).toFixed(2)} KB
                                </span>
                            </div>
                            <button
                                className="remove-btn"
                                onClick={() => removeFile(index)}
                                title="Remove file"
                            >
                                <i className="fa fa-trash" />
                            </button>
                        </div>
                    ))}
                </div>
            }
        </div >
    );
}
