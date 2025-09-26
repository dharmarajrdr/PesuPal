import { useDispatch } from 'react-redux';
import Media from '../../Media';
import './AttachmentPreview.css';
import { showPopup } from '../../store/reducers/PopupSlice';
import AttachmentItem from './AttachmentItem';
import { useState } from 'react';
import { hideLoader, showLoader } from '../../store/reducers/VerticalLoaderSlice';

const AttachmentPreview = ({ files, setFiles, afterUpload }) => {

    const dispatch = useDispatch();
    const [sending, setSending] = useState(false);

    const removeFile = (index) => {
        setFiles((prev) => {
            const updated = [...prev];
            updated.splice(index, 1);
            return updated;
        });
    };

    const handleUpload = () => {

        if (sending) {
            return dispatch(showPopup({ message: 'Please wait, files are being sent.', type: 'error' }));
        }

        setSending(true);

        dispatch(showLoader());

        function sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }

        Media.uploadMultipleMedia(files, setFiles).then(async () => {

            dispatch(showPopup({ message: 'Files sent successfully!', type: 'success' }));

            for (const { mediaId, file, extension, size } of files) {
                const media = { mediaId, 'name': file.name, extension, size };
                afterUpload && afterUpload({ media });
                await sleep(10); // wait 10ms before next send
            }

            setFiles([]);
            setSending(false);
            dispatch(hideLoader());
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setSending(false);
            dispatch(hideLoader());
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
                        {!sending && <button id="cancel-button" onClick={() => setFiles([])}>Cancel</button>}
                        <button id="send-button" onClick={handleUpload}>{sending ? 'Sending...' : 'Send'}</button>
                    </div>
                </div>
            </div>
        </div>
    ) : null;
}

export default AttachmentPreview