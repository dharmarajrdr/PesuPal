import utils from '../../utils'
import UploadStatus from './UploadStatus'

const AttachmentItem = ({ file, index, removeFile }) => {
    return (
        <div key={index} className="attachment-item">
            <div className="file-preview">
                {file.preview ? (
                    <img src={file.preview} alt={file.file.name} className="file-thumbnail" />
                ) : (
                    <i className={`${utils.getFileIcon(file.file)} file-icon`} />
                )}
            </div>
            <div className="file-info FCSS">
                <span className="file-name mB5">{file.file.name}</span>
                <span className="file-size">
                    {utils.formatFileSize(file.file.size)}
                </span>
            </div>
            <UploadStatus index={index} file={file} removeFile={removeFile} />
        </div>
    )
}

export default AttachmentItem