import utils from "./utils";

const BASE_URL = 'http://localhost:8080'; // Adjust the base URL as needed

export async function apiRequest(endpoint, method = 'GET', data = null, customHeaders = {}) {

    const token = utils.parseCookie().get('token');
    const orgId = sessionStorage.getItem('org-id'); // adjust as needed

    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'X-Org-Id': orgId || null,
        ...customHeaders,
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        method,
        headers,
    };

    if (data && method !== 'GET') {
        config.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, config);
        const contentType = response.headers.get('content-type');

        if (!response.ok) {
            const errorResponse = contentType?.includes('application/json')
                ? await response.json()
                : { message: await response.text() };
            throw new Error(errorResponse.message || 'API request failed');
        }

        return contentType?.includes('application/json')
            ? await response.json()
            : await response.text();
    } catch (error) {
        const { message } = JSON.parse(error.message) || {};
        console.log(message);
        if (message == "Invalid JWT token") {
            document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/';
            window.location.reload();   // Reload the page to clear the session
        }
        throw error;
    }
}
