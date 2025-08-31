import { useDispatch } from "react-redux";
import { apiRequest } from "../http_request";
import { updateMemberStatusInDepartment } from "../store/reducers/DepartmentSlice";
import { updateMemberStatusInPeople } from "../store/reducers/PeopleSlice";

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
            const payload = { 'userId': message.orgMemberId, 'status': message.memberStatus };
            dispatch(updateMemberStatusInDepartment(payload));
            dispatch(updateMemberStatusInPeople(payload));
        }
    };
}