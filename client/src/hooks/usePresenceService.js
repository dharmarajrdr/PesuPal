import { useDispatch } from "react-redux";
import { apiRequest } from "../http_request";
import { updateMemberStatusInPeople } from "../store/reducers/PeopleSlice";
import { updateMemberStatusInDepartment } from "../store/reducers/DepartmentSlice";
import { updateMemberStatusInRecentChats } from "../store/reducers/RecentChatsSlice";
import { updateMemberStatusInPinnedDirectMessages } from "../store/reducers/PinnedDirectMessageSlice";

export default function usePresenceService() {

    const dispatch = useDispatch();
    const setUserOnline = () => {
        if (document.hidden) { return; }    // Don't inform if the tab is not active
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
            dispatch(updateMemberStatusInPinnedDirectMessages(payload));
            dispatch(updateMemberStatusInRecentChats(payload));
        }
    };
}