import { useDispatch } from 'react-redux';
import Media from '../../Media';
import './AttachmentPreview.css';
import { showPopup } from '../../store/reducers/PopupSlice';
import AttachmentItem from './AttachmentItem';

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
                    {files.map((file, index) => (
                        <AttachmentItem key={index} file={file} index={index} removeFile={removeFile} />
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