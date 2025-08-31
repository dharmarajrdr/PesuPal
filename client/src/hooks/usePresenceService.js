import { useDispatch } from "react-redux";
import { apiRequest } from "../http_request";
import { updateMemberStatus } from "../store/reducers/DepartmentSlice";

export default function usePresenceService() {

    const dispatch = useDispatch();
    const setUserOnline = () => {
        apiRequest(`/api/v1/presence/inform`, 'PATCH').catch(({ message }) => {
            console.error(message);
        });
    };

    return {
        informUserOnlineAtInterval: (seconds) => {
            setUserOnline();
            const interval = setInterval(setUserOnline, seconds * 1000);
            return () => clearInterval(interval);
        },
        onPresenceUpdate: (message) => {
            dispatch(updateMemberStatus({ 'userId': message.orgMemberId, 'status': message.memberStatus }));
        }
    };
}