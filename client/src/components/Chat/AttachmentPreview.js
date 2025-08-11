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
                            <i className="fa fa-trash remove-btn" onClick={() => removeFile(index)} title="Remove file" />
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
                        <button id="send-button" onClick={() => {/* Handle send files */ }}>Send</button>
                    </div>
                </div>
            </div>
        </div>
    ) : null;
}

export default AttachmentPreview