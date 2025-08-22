
const UploadStatus = ({ file, removeFile, index }) => {

    const { uploading, uploaded, failedUpload, failedUploadReason } = file;
    return (
        <>
            {uploaded ? (
                <i className="fa fa-check-circle file-uploaded-icon" title="File uploaded successfully" />
            ) : failedUpload ? (
                <i className="fa fa-exclamation-triangle file-upload-failed-icon" title={failedUploadReason} />
            ) : uploading ? (
                <i className="fa fa-spinner fa-spin file-uploading-icon" title="File is uploading" />
            ) : (
                <i className="fa fa-trash remove-btn" onClick={() => removeFile(index)} title="Remove file" />
            )}
        </>
    )
}

export default UploadStatus