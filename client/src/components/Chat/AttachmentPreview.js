import { useDispatch } from 'react-redux';
import Media from '../../Media';
import utils from '../../utils';
import './AttachmentPreview.css';
import { showPopup } from '../../store/reducers/PopupSlice';

const AttachmentPreview = ({ files, setFiles }) => {

    const dispatch = useDispatch();

    const removeFile = (index) => {
        setFiles((prev) => {
            const updated = [...prev];
            updated.splice(index, 1);
            return updated;
        });
    };

    const handleUpload = () => {

        Media.uploadMultipleMedia(files, setFiles).then(() => {
            dispatch(showPopup({ message: 'Files sent successfully!', type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    };

    return files.length > 0 ? (
        <div className='entire-screen-overlay FRCC'>
            <div id='attachment-preview' className='centerMe FCSB'>
                <h4 id='attachment-preview-title' className='w100'>Attachment Preview</h4>
                <div className="w100 FCSS" id='attachment-preview-file-list'>
                    {files.map(({ preview, file, uploading, uploaded, failedUpload, failedUploadReason }, index) => (
                        <div key={index} className="attachment-item">
                            <div className="file-preview">
                                {preview ? (
                                    <img src={preview} alt={file.name} className="file-thumbnail" />
                                ) : (
                                    <i className={`${utils.getFileIcon(file)} file-icon`} />
                                )}
                            </div>
                            <div className="file-info FCSS">
                                <span className="file-name mB5">{file.name}</span>
                                <span className="file-size">
                                    {utils.formatFileSize(file.size)}
                                </span>
                            </div>
                            {uploaded ? (
                                <i className="fa fa-check-circle file-uploaded" title="File uploaded successfully" />
                            ) : failedUpload ? (
                                <i className="fa fa-exclamation-triangle file-upload-failed" title={failedUploadReason} />
                            ) : uploading ? (
                                <i className="fa fa-spinner fa-spin file-uploading" title="File is uploading" />
                            ) : (
                                <i className="fa fa-trash remove-btn" onClick={() => removeFile(index)} title="Remove file" />
                            )}
                        </div>
                    ))}
                </div>
                <div className='w100 FRCB' id='attachment-preview-actions'>
                    <div className='FRCS'>
                        {files.length > 0 && <span id="attachment-preview-count">
                            {files.length} {files.length === 1 ? 'file' : 'files'} selected
                        </span>}
                    </div>
                    <div className='FRCE'>
                        <button id="cancel-button" onClick={() => setFiles([])}>Cancel</button>
                        <button id="send-button" onClick={handleUpload}>Send</button>
                    </div>
                </div>
            </div>
        </div>
    ) : null;
}

export default AttachmentPreview