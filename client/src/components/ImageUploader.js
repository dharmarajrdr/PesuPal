import "./ImageUploader.css";
import { useRef, useState } from "react";

const ImageContainer = ({ image, onEditClick, onDeleteClick, allowEdit }) => {

    return <div id="image-container">
        {allowEdit && <div className="FRCC centerMe" id="actionButtons">
            <i className="fa fa-edit mR5" aria-hidden="true" onClick={onEditClick}></i>
            <i className="fa fa-trash mL5" aria-hidden="true" onClick={onDeleteClick}></i>
        </div>}
        <img src={image} alt="Uploaded" />
    </div>
}

const Placeholder = ({ onClick, placeholder }) => {
    return <div className="FRCC" id="placeholder" onClick={onClick}>
        <i className="fas fa-image"></i> {placeholder || "Upload"}
    </div>
}

const ImageUploader = ({ defaultImage, onImageSelect, style, allowEdit, placeholder }) => {

    allowEdit = allowEdit != null ? allowEdit : true;

    const fileInputRef = useRef(null);
    const [image, setImage] = useState(defaultImage || null);

    const handleImageClick = () => {
        if (allowEdit) {
            fileInputRef.current.click();
        }
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const imageUrl = URL.createObjectURL(file);
            setImage(imageUrl);
            if (onImageSelect) {
                onImageSelect(file);
            }
        }
    };

    const onDeleteClick = () => {
        if (allowEdit) {
            setImage(null);
            onImageSelect && onImageSelect(null);
        }
    };

    return (
        <div className="image-uploader FRCC">
            <div id="image-preview" style={{ ...style }}>
                {image ?
                    <ImageContainer allowEdit={allowEdit} image={image} onEditClick={handleImageClick} onDeleteClick={onDeleteClick} /> :
                    <Placeholder onClick={handleImageClick} placeholder={placeholder} />
                }
            </div>
            <input type="file" accept="image/*" ref={fileInputRef} style={{ display: "none" }} onChange={handleFileChange} />
        </div>
    );
};

export default ImageUploader;
