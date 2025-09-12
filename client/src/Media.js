import { apiRequest } from "./http_request";

async function uploadSingleMedia(file, setFile) {
    const formData = new FormData();
    if (setFile) {
        setFile(prevFile => ({ ...prevFile, 'uploading': true }));
    }
    formData.append("file", file.file, file.file.name);
    const data = await apiRequest(`/api/v1/media/upload`, 'POST', formData);
    if (setFile) {
        setFile(prevFile => ({ ...prevFile, 'uploaded': true }));
    }
    return data;
}

async function uploadMultipleMedia(files, setFiles) {

    if (!files || !files.length) return;

    setFiles(prevFiles => prevFiles.map(f => ({ ...f, 'uploading': true })));

    for (const file of files) {
        if (file.existing) { // skip existing files
            setFiles(prevFiles => prevFiles.map(f => f.file.name === file.file.name ? { ...f, 'uploaded': true, 'uploading': false } : f));
            continue;
        }
        try {
            const { data } = await uploadSingleMedia(file);
            const { mediaId, extension, size } = data;
            Object.assign(file, { mediaId, extension, size, 'uploaded': true });
            setFiles(prevFiles => prevFiles.map(f => f.file.name === file.file.name ? { ...f, ...file } : f));
        } catch ({ message }) {
            setFiles(prevFiles => prevFiles.map(f => f.file.name === file.file.name ? { ...f, 'failedUpload': true, 'failedUploadReason': message } : f));
        }
    }
}

export default {
    uploadMultipleMedia,         // for multiple files
    uploadSingleMedia    // for one file
};
