import utils from "./utils";

export async function apiRequest(endpoint, method = 'GET', data = null, customHeaders = {}) {

    const token = sessionStorage.getItem('token') || utils.parseCookie().get('token');

    const headers = {
        'Accept': 'application/json',
        ...customHeaders
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = { method, headers };

    if (data && method !== 'GET') {
        if (data instanceof FormData) {
            // Let the browser set the Content-Type for FormData (includes the boundary)
            config.body = data;
        } else {
            headers['Content-Type'] = 'application/json';
            config.body = JSON.stringify(data);
        }
    }

    try {
        const response = await fetch(`${utils.serverDomain}${endpoint}`, config);
        const contentType = response.headers.get('content-type') || '';
        const isJson = contentType.includes('application/json');
        const statusCode = response.status;

        const result = isJson
            ? await response.json()
            : { message: await response.text(), status: 'FAILURE' };

        Object.assign(result, { statusCode });

        if (!response.ok || result.status === 'FAILURE') {
            const { data } = result;
            const noRedirectionRequired = ['/', '/org/create'];

            if (statusCode == 401) {
                sessionStorage.removeItem('token');
                document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/';
                window.location.href = '/signin';
            } else if (statusCode == 307 && !noRedirectionRequired.includes(window.location.pathname)) {
                window.location.pathname = data.redirect;
            }
            throw result;
        }

        return result;
    } catch (error) {
        throw {
            message: error?.message || 'An error occurred while processing your request.',
            status: 'FAILURE',
            statusCode: error?.statusCode
        };
    }
}
