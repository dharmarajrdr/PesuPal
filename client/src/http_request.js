import utils from "./utils";

const BASE_URL = 'http://localhost:8080'; // Update as needed

export async function apiRequest(endpoint, method = 'GET', data = null, customHeaders = {}) {
    const token = utils.parseCookie().get('token');
    const orgId = sessionStorage.getItem('org-id'); // Optional: get orgId from session

    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        ...(orgId && { 'X-Org-Id': orgId }),
        ...customHeaders,
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = { method, headers };

    if (data && method !== 'GET') {
        config.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, config);
        const contentType = response.headers.get('content-type') || '';

        const isJson = contentType.includes('application/json');
        const result = isJson ? await response.json() : {
            message: await response.text(),
            status: 'FAILURE',
        };

        if (!response.ok || result.status === 'FAILURE') {
            if (response.status == 401) {
                document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/';
                // window.location.reload(); // Auto logout
            }
            throw result;
        }

        return result; // { message, data, info, status }

    } catch (error) {
        const fallbackError = {
            message: error?.message || 'Something went wrong',
            status: 'FAILURE'
        };

        throw typeof error === 'object' && error.message ? error : fallbackError;
    }
}
