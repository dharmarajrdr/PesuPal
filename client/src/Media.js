import { apiRequest } from "./http_request";

async function uploadSingleMedia(file) {
    const formData = new FormData();
    formData.append("file", file.file, file.file.name);
    return await apiRequest(`/api/v1/media/upload`, 'POST', formData);
}

async function uploadMultipleMedia(files, setFiles) {
    const uploadedFiles = [];
    for (const file of files) {
        try {
            const { data } = await uploadSingleMedia(file);
            const { name: mediaId, extension, size } = data;
            Object.assign(file, { mediaId, extension, size, uploaded: true });
            uploadedFiles.push(file);
        } catch ({ message }) {
            throw { message };
        }
    }
    return uploadedFiles;
}

export default {
    uploadMultipleMedia,         // for multiple files
    uploadSingleMedia    // for one file
};
