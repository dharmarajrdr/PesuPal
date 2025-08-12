import { apiRequest } from "./http_request";

async function uploadSingleMedia(file) {
    const formData = new FormData();
    formData.append("file", file.file, file.file.name);
    return await apiRequest(`/api/v1/media/upload`, 'POST', formData);
}

async function uploadMultipleMedia(files, setFiles) {

    setFiles(prevFiles => prevFiles.map(f => ({ ...f, uploading: true })));
    for (const file of files) {
        try {
            const { data } = await uploadSingleMedia(file);
            const { name: mediaId, extension, size } = data;
            Object.assign(file, { mediaId, extension, size, uploaded: true });
            setFiles(prevFiles => prevFiles.map(f => f.file.name === file.file.name ? { ...f, ...file } : f));
        } catch ({ message }) {
            setFiles(prevFiles => prevFiles.map(f => f.file.name === file.file.name ? { ...f, failedUpload: true, failedUploadReason: message } : f));
        }
    }
}

export default {
    uploadMultipleMedia,         // for multiple files
    uploadSingleMedia    // for one file
};
