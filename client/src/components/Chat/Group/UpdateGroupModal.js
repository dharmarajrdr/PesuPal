import './CreateGroupModal.css';
import { useState } from 'react';
import GroupVisibility from './GroupVisibility';
import ImageUploader from '../../ImageUploader';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from '../../../store/reducers/PopupSlice';
import { updateRecentChat } from '../../../store/reducers/RecentChatsSlice';
import { updateCurrentChatPreview } from '../../../store/reducers/CurrentChatPreviewSlice';
import { updatePinnedDirectMessage } from '../../../store/reducers/PinnedDirectMessageSlice';
import Media from '../../../Media';
import { hideLoader, showLoader } from '../../../store/reducers/VerticalLoaderSlice';

const UpdateGroupModal = ({ setShowCreateGroupModal, groupData }) => {

    const dispatch = useDispatch();

    const { chatId: groupId, displayName: name, displayPicture, description, visibility } = groupData || {};
    const [file, setFile] = useState(null);
    const [groupName, setGroupName] = useState(name);
    const [isPublic, setIsPublic] = useState(visibility === 'PUBLIC');
    const [groupDescription, setGroupDescription] = useState(description);

    const currentChatPreview = useSelector(state => state.currentChatPreviewSlice);
    const { groupChatConfiguration } = currentChatPreview || {};
    const { changeName: groupNameEditable, changeDescription: groupDescriptionEditable, changeVisibility: groupVisibilityEditable, changeProfilePicture: groupProfilePictureEditable } = groupChatConfiguration || {};

    const closeUpdateGroupModal = () => {
        setGroupName('');
        setGroupDescription('');
        setIsPublic(true);
        setShowCreateGroupModal(false);
    }

    const handleSubmit = () => {

        if (!groupName.trim()) {
            alert("Group name can't be empty!");
            return;
        }

        dispatch(showLoader());

        const groupData = {
            name: groupName.trim(),
            displayPicture,
            description: groupDescription.trim(),
            visibility: isPublic ? 'PUBLIC' : 'PRIVATE',
        };

        const updateGroup = function (groupData) {

            apiRequest(`/api/v1/group/${groupId}`, 'PUT', groupData).then(({ data, message }) => {
                dispatch(hideLoader());
                dispatch(showPopup({ message, type: 'success' }));
                const { name, description, visibility, displayPicture } = data || {};
                dispatch(updateCurrentChatPreview({
                    'displayName': name,
                    displayPicture,
                    description,
                    visibility
                }));
                dispatch(updateRecentChat({
                    'chatId': groupId,
                    name,
                    'image': displayPicture,
                    visibility
                }));
                dispatch(updatePinnedDirectMessage({
                    'chatId': groupId,
                    'displayName': name,
                    displayPicture,
                    visibility
                }));
                closeUpdateGroupModal();
            }).catch(({ message }) => {
                dispatch(hideLoader());
                dispatch(showPopup({ message, type: 'error' }));
            });
        }

        if (file == null) {
            if (displayPicture != null) {
                Object.assign(groupData, { 'displayPictureRemoved': true });
            }
            updateGroup(groupData);
        } else {
            Media.uploadSingleMedia({ file }).then(({ data }) => {
                const { mediaId } = data || {};
                Object.assign(groupData, { 'displayPicture': mediaId });
                updateGroup(groupData);
            }).catch(({ message }) => {
                dispatch(hideLoader());
                dispatch(showPopup({ message, type: 'error' }));
            });
        }

    };

    return (
        <div className="FCCC entire-screen-overlay" id='update-group-modal'>
            <div id="update-group-modal-content" className="FCCC modal-box">

                <h2>Group Info</h2>

                <div className='FCSS w100'>
                    <ImageUploader allowEdit={groupProfilePictureEditable} defaultImage={displayPicture} onImageSelect={(file) => setFile(file)} style={{ width: '100px', height: '100px', marginBottom: '20px' }} />
                    <input type="text" placeholder="Enter group name" value={groupName} onChange={(e) => groupNameEditable ? setGroupName(e.target.value) : null} className="group-name-input" />
                    <input type="text" placeholder="Enter group description (optional)" value={groupDescription} onChange={(e) => groupDescriptionEditable ? setGroupDescription(e.target.value) : null} className="group-description-input" />
                    <GroupVisibility isPublic={isPublic} setIsPublic={setIsPublic} groupVisibilityEditable={groupVisibilityEditable} />
                </div>

                <div className='FRCC w100'>
                    <button className="cancel-btn mR5" onClick={closeUpdateGroupModal}>Cancel</button>
                    <button className="submit-btn mL5" onClick={handleSubmit}>Update</button>
                </div>
            </div>
        </div>
    );
};

export default UpdateGroupModal;
