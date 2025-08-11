import utils from '../../utils';
import './AttachmentPreview.css';

const AttachmentPreview = ({ files, setFiles }) => {

    const removeFile = (index) => {
        setFiles((prev) => {
            const updated = [...prev];
            updated.splice(index, 1);
            return updated;
        });
    };

    const sendFiles = async () => {
        for (const file of files) {
            if (file.preview) {
                // Here you would typically handle the file upload logic
                console.log(`Sending file: ${file.file.name}`);

            }
        }
    }

    return files.length > 0 ? (
        <div className='entire-screen-overlay FRCC'>
            <div id='attachment-preview' className='centerMe FCSB'>
                <h4 id='attachment-preview-title' className='w100'>Attachment Preview</h4>
                <div className="w100 FCSS" id='attachment-preview-file-list'>
                    {files.map(({ preview, file }, index) => (
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
                            {file.uploaded ? (
                                <i className="fa fa-check-circle file-uploaded" title="File uploaded successfully" />
                            ) : file.uploading ? (
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
                        <button id="send-button" onClick={sendFiles}>Send</button>
                    </div>
                </div>
            </div>
        </div>
    ) : null;
}

export default AttachmentPreview