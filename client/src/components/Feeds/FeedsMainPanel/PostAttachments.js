import './PostAttachments.css';
import { useDispatch } from "react-redux";
import UploadStatus from "../../Chat/UploadStatus";
import { showFullScreenImageAt } from "../../../store/reducers/FullScreenImageSlice";

const FilePreview = ({ file, removeSelectedFileHandler, showFullScreenImageHandler }) => {

    const { name, preview } = file || {};

    return <div className='post-attachment-preview FRCC' key={name}>
        <img src={preview} alt={name} className='post-attachment-image' onClick={showFullScreenImageHandler} />
        <UploadStatus file={file} removeFile={() => removeSelectedFileHandler(file)} />
    </div>
}

const PostAttachments = ({ files, removeSelectedFileHandler }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (index) => {
        dispatch(showFullScreenImageAt({
            mediaUrls: files.map(f => f.preview),
            currentIndex: index
        }));
    }

    return files.length > 0 ? <div id='post-attachments' className='FRCS w100'>
        {files.map((file, index) => (
            <FilePreview key={index} file={file} removeSelectedFileHandler={removeSelectedFileHandler} showFullScreenImageHandler={() => showFullScreenImageHandler(index)} />
        ))}
    </div> : null;
}

export default PostAttachments