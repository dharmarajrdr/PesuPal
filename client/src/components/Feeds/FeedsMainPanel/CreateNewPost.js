import './CreateNewPost.css';
import Media from '../../../Media';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import SearchUser from '../../SearchUser';
import UploadStatus from '../../Chat/UploadStatus';
import { apiRequest } from '../../../http_request';
import { useEffect, useRef, useState } from 'react';
import ShareWithSchedule from './ShareWithSchedule';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { showFullScreenImageAt } from '../../../store/reducers/FullScreenImageSlice';
import { hideLoader, showLoader } from '../../../store/reducers/VerticalLoaderSlice';
import { hideConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { addPost, hideCreatePostModal, resetPostData, updatePost } from '../../../store/reducers/PostSlice';

const predefinedLabels = ['cc', 'behalf of', 'with', 'credits', 'kudos', 'thanks', 'shoutout'];

const PostTagContainer = ({ tags, setTags }) => {

    const dispatch = useDispatch();

    const addTagHandler = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            const newTag = `#${e.target.value.trim()}`;
            if (newTag.match(/^#[\w-]+$/) === null) {
                return dispatch(showPopup({ message: "Invalid tag format! Tags can only contain letters, numbers, underscores, and hyphens.", type: 'error' }));
            }
            if (newTag && !tags.includes(newTag)) {
                setTags([...tags, newTag]);
                e.target.value = '';
            }
        }
    }

    const removeTagHandler = (e) => {
        const tagToRemove = e.target.previousSibling.textContent;
        setTags(tags.filter(tag => tag !== tagToRemove));
    }

    return <div className='FRCS' id='create-post-tags'>
        {tags.map((tag, index) => (
            <div className='create-post-tag FRCC' key={index}>
                <span>{tag}</span>
                <i className="fa-solid fa-xmark" onClick={removeTagHandler}></i>
            </div>
        ))}
        <input type='text' placeholder='Add Tag' autoComplete='off' id='create-tag-input' onKeyDown={addTagHandler} />
    </div>
}

const PostAttachments = ({ files, removeSelectedFileHandler }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (index) => {
        dispatch(showFullScreenImageAt({
            mediaUrls: files.map(f => f.preview),
            currentIndex: index
        }));
    }

    return files.length > 0 ? <div id='post-attachments' className='FRCS w100'>
        {files.map((file, index) => (
            <FilePreview key={index} file={file} removeSelectedFileHandler={removeSelectedFileHandler} showFullScreenImageHandler={() => showFullScreenImageHandler(index)} />
        ))}
    </div> : null;
}

const FilePreview = ({ file, removeSelectedFileHandler, showFullScreenImageHandler }) => {

    const { name, preview } = file || {};

    return <div className='post-attachment-preview FRCC' key={name}>
        <img src={preview} alt={name} className='post-attachment-image' onClick={showFullScreenImageHandler} />
        <UploadStatus file={file} removeFile={() => removeSelectedFileHandler(file)} />
    </div>
}

const PostAction = ({ icon, label, onClick }) => (
    <span className='actions_post_creation FRCC' onClick={onClick ? onClick : null}>
        <i className={`${icon} mR5`}></i>
        <span>{label}</span>
    </span>
);

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

const CreateNewPost = () => {

    const { currentPostData, isShowCreatePostModal } = useSelector(state => state.posts) || {};

    const dispatch = useDispatch();
    const fileInputRef = useRef(null);
    const [files, setFiles] = useState([]);
    const myProfile = useSelector(state => state.myProfile);
    const [tags, setTags] = useState([]);
    const [postId, setPostId] = useState(null);
    const [mentionLabel, setMentionLabel] = useState(null);
    const [mentionedMembers, setMentionedMembers] = useState([]);
    const [header, setHeader] = useState('Post Something');
    const [isPostCreation, setIsPostCreation] = useState(false);
    const [postTitle, setPostTitle] = useState("");
    const [content, setContent] = useState("");
    const [scheduledAt, setScheduledAt] = useState(null);
    const [isScheduledPost, setIsScheduledPost] = useState(false);
    const [showMentionContainer, setShowMentionContainer] = useState(false);

    const allowedTypes = ['image/png', 'image/jpeg', 'image/gif'];

    useEffect(() => {

        if (currentPostData) {
            setHeader('Edit Post');
            setPostTitle(currentPostData?.title || "");
            setContent(currentPostData?.description || "");
            setTags(currentPostData?.tags || []);
            setPostId(currentPostData?.id || null);
            setFiles(currentPostData?.media?.map(({ url, mediaId, extension }) => ({ 'preview': url, mediaId, 'name': mediaId, 'file': { 'name': mediaId }, extension, 'existing': true })) || []);
            setMentionLabel(currentPostData?.mentions?.label || null);
            setMentionedMembers(currentPostData?.mentions?.data || []);
            setIsPostCreation(false);
            setIsScheduledPost(currentPostData?.status === 'SCHEDULED');
            setScheduledAt(currentPostData?.status === 'SCHEDULED' ? currentPostData?.createdAt : null);
            setShowMentionContainer(currentPostData?.mentions?.data?.length > 0);
        } else {
            setHeader('Post Something');
            setPostTitle("");
            setContent("");
            setTags([]);
            setFiles([]);
            setPostId(null);
            setMentionLabel(predefinedLabels[0]);
            setMentionedMembers([]);
            setIsPostCreation(true);
            setIsScheduledPost(false);
            setScheduledAt(null);
            setShowMentionContainer(false);
        }

        const quillEditor = document.querySelector('.ql-container.ql-snow');
        const quillToolbar = document.querySelector('.ql-toolbar.ql-snow');
        const fullscreenQuillEditor = document.querySelector('.fullscreen-post-creation .ql-editor');
        const inputWrapper = document.getElementById('post-content-input-wrapper');
        const createPostTags = document.getElementById('create-post-tags');
        const postInput = document.getElementById('post-input');
        const postAttachments = document.getElementById('post-attachments');

        if (quillEditor) {
            quillEditor.style.border = 'none';
        }
        if (quillToolbar) {
            quillToolbar.style.border = 'none';
            quillToolbar.style.borderBottom = '1px solid #ccc';
        }
        if (postInput) {
            // postInput.style.border = '1px solid blue';
            postInput.style.height = `calc(100% - ${createPostTags.offsetHeight}px)`;
        }
        if (fullscreenQuillEditor) {
            // fullscreenQuillEditor.style.border = '1px solid orange';
            fullscreenQuillEditor.style.height = `calc(100% - 40px)`;
        }
        if (inputWrapper) {
            inputWrapper.style.height = `calc(470px - ${postAttachments ? postAttachments.offsetHeight : 0}px)`;
        }

    }, [currentPostData, isShowCreatePostModal]);

    const postCreation = (api, method, options) => {

        if (content.trim().length == 0) {
            return dispatch(showPopup({ message: "Post content cannot be empty!", type: 'error' }));
        }

        dispatch(showLoader());

        Media.uploadMultipleMedia(files, setFiles).then(() => {

            const payload = {
                "title": postTitle,
                "description": content,
                "tags": tags,
                "mediaIds": files.map(file => {
                    const { mediaId, extension } = file || {};
                    return {
                        'id': mediaId, extension
                    }
                }),
                "mentions": {
                    "label": mentionLabel,
                    "data": mentionedMembers.map(({ id }) => id)
                },
                // "poll": {
                //     "question": "Which company are you targeting?",
                //     "options": [
                //         "Microsoft", "PayPal", "Google", "Amazon"
                //     ]
                // }
            };

            Object.assign(payload, options || {});

            apiRequest(api, method, payload).then(({ data, message }) => {
                isPostCreation ? dispatch(addPost(data)) : dispatch(updatePost(data));
                onMinimize();
                dispatch(showPopup({ message, type: 'success' }));
                dispatch(hideLoader());
                dispatch(hideConfirmationPopup());
            }).catch(({ message }) => {
                dispatch(showPopup({ message, type: 'error' }));
                dispatch(hideLoader());
                dispatch(hideConfirmationPopup());
            });

        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            dispatch(hideLoader());
            dispatch(hideConfirmationPopup());
        });

    }

    const handlePostSubmit = () => {

        postCreation(`/api/v1/post/${isPostCreation ? 'create' : postId}`, isPostCreation ? 'POST' : 'PATCH');
    };

    const handlePostSchedule = (scheduledAt) => {

        if (!isPostCreation && !isScheduledPost) {
            return dispatch(showPopup({ message: "Unable to schedule as this post is already published.", type: 'error' }));
        }

        postCreation(`/api/v1/post/${isPostCreation ? 'schedule' : 'reschedule/' + postId}`, isPostCreation ? 'POST' : 'PATCH', { scheduledAt });
    };

    const onMinimize = () => {

        dispatch(resetPostData());
        dispatch(hideCreatePostModal());
    }

    const handleFileChange = (e) => {

        const selectedFiles = Array.from(e.target.files);

        const maxFiles = 5;
        const maxFileSize = 1 * 1024 * 1024; // 1 MB

        if (maxFiles && selectedFiles.length + files.length > maxFiles) {
            return dispatch(showPopup({ message: `You can only upload a maximum of ${maxFiles} files.`, type: 'error' }));
        }

        const filtered = allowedTypes.length
            ? selectedFiles.filter((file) =>
                allowedTypes.some((type) => file.type.includes(type))
            )
            : selectedFiles;

        if (filtered.length < selectedFiles.length) {
            return dispatch(showPopup({ message: "Some files were not allowed based on file type restrictions.", type: 'error' }));
        }

        if (maxFileSize && filtered.some((file) => file.size > maxFileSize)) {
            return dispatch(showPopup({ message: `File size exceeds the maximum limit of ${maxFileSize / 1024 / 1024} MB.`, type: 'error' }));
        }

        const withPreview = filtered.map((file) => ({
            file,
            preview: file.type.startsWith("image") ? URL.createObjectURL(file) : null
        }));

        setFiles((prev) => [...prev, ...withPreview]);
        e.target.value = ""; // Reset file input
    };

    const removeSelectedFileHandler = (file) => {
        setFiles(files.filter(f => f.name !== file.name));
    }

    const showMentionContainerHandler = () => {
        setShowMentionContainer(true);
    }

    return isShowCreatePostModal && (
        <div id='create-new-post-overlay' className='entire-screen-overlay fullscreen-post-creation FRCC'>
            <div id='CreateNewPost' className='FCSS post-container'>
                <div className='FRCB w100'>
                    <label className='post-label'>{header}</label>
                </div>

                <div className='FRSS w100' id='post-input-section'>
                    <img src={myProfile?.displayPicture} className='img_40_40 user-avatar' alt='User' />
                    <div className='FCSS w100' id='post-input-wrapper'>
                        <input type='text' placeholder='Title' id='post-title-input' autoComplete='off' value={postTitle} onChange={(e) => setPostTitle(e.target.value)} />
                        <div className='FCSS w100' id='post-content-input-wrapper'>
                            <ReactQuill theme="snow" value={content} onChange={setContent} className='w100' id='post-input' placeholder='What do you want to share?' />
                            <PostTagContainer tags={tags} setTags={setTags} />
                        </div>
                        {showMentionContainer && <PostMentions mentionLabel={mentionLabel} mentionedMembers={mentionedMembers} setMentionLabel={setMentionLabel} setMentionedMembers={setMentionedMembers} />}
                        <PostAttachments files={files} removeSelectedFileHandler={removeSelectedFileHandler} />
                    </div>
                </div>

                <div className='w100 FRCB post-footer'>
                    <div className='FRCS post-actions'>
                        <PostAction icon='fa-solid fa-square-poll-vertical' label='Poll' />
                        <PostAction icon='fa-solid fa-user-tag' label='Mention' onClick={showMentionContainerHandler} />
                        <PostAction icon='fa-regular fa-image' label='Attachment' onClick={() => fileInputRef.current.click()} />
                        <input type='file' multiple style={{ display: 'none' }} ref={fileInputRef} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} />
                    </div>
                    <div className='FRCE'>
                        <button id='cancel-post-button' onClick={onMinimize}>Cancel</button>
                        <ShareWithSchedule onShare={handlePostSubmit} onSchedule={handlePostSchedule} scheduledAt={scheduledAt} />
                    </div>
                </div>

            </div>
        </div>
    );
};


export default CreateNewPost;
