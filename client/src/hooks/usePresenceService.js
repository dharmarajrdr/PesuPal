import { apiRequest } from "../http_request";

export default function usePresenceService() {

    return {
        setUserOnline: () => {
            apiRequest(`/api/v1/presence/inform`, 'PATCH').then(({ message }) => {
                console.log(message);
            }).catch(({ message }) => {
                console.error(message);
            });
        }
    };
}