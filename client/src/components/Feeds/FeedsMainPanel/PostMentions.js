import './PostMentions.css';
import { useDispatch } from "react-redux";
import SearchUser from "../../SearchUser";
import { showProfile } from "../../../store/reducers/ProfileSlice";

const predefinedLabels = ['cc', 'behalf of', 'with', 'credits', 'kudos', 'thanks', 'shoutout'];

const PostMentions = ({ mentionLabel, mentionedMembers, setMentionLabel, setMentionedMembers }) => {

    const maxMentions = 5;
    const dispatch = useDispatch();

    return <div className='FRCS w100 post-mentions' id='create-post-mentions'>
        <SearchUser maxUsersSelectable={maxMentions} selectedUsers={mentionedMembers} setSelectedUsers={setMentionedMembers} />
        <select id='mention-label-select' value={mentionLabel || ''} onChange={(e) => setMentionLabel(e.target.value)}>
            {predefinedLabels.map((predefinedLabel, index) => (
                <option key={index} value={predefinedLabel}>
                    {predefinedLabel}
                </option>
            ))}
        </select>
        {mentionedMembers.map((mention) => {
            const { id, displayName } = mention || {};
            return <div key={id} className='mentioned-member' onClick={() => dispatch(showProfile(id))}>
                <span className='display-name'>{displayName}</span>
            </div>
        })}
    </div>
}

export default PostMentions