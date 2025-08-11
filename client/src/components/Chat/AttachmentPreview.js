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
            <div id='attachment-preview' className='centerMe'>
                <div className="w100 FCSS mB10">
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
            </div>
        </div>
    ) : null;
}

export default AttachmentPreview