import './CreateNewPost.css';
import PostPoll from './PostPoll';
import Media from '../../../Media';
import utils from '../../../utils';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import PostMentions from './PostMentions';
import PostAttachments from './PostAttachments';
import PostTagContainer from './PostTagContainer';
import { apiRequest } from '../../../http_request';
import { useEffect, useRef, useState } from 'react';
import ShareWithSchedule from './ShareWithSchedule';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { hideLoader, showLoader } from '../../../store/reducers/VerticalLoaderSlice';
import { addPost, hideCreatePostModal, resetPostData, updatePost } from '../../../store/reducers/PostSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const PostAction = ({ icon, label, onClick }) => (
    <span className='actions_post_creation FRCC' onClick={onClick ? onClick : null}>
        <i className={`${icon} w15 mR5`}></i>
        <span>{label}</span>
    </span>
);

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
    const [showTitle, setShowTitle] = useState(false);
    const [showPollContainer, setShowPollContainer] = useState(false);
    const [question, setQuestion] = useState('');
    const [pollOptions, setPollOptions] = useState(['', '']);
    const [purpose, setPurpose] = useState('CREATE');

    const allowedTypes = ['image/png', 'image/jpeg', 'image/gif'];

    useEffect(() => {

        if (currentPostData) {
            setHeader('Edit Post');
            setPostTitle(currentPostData?.title || "");
            setShowTitle(currentPostData?.title?.trim().length > 0);
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
            setPurpose('EDIT');
        } else {
            setHeader('Post Something');
            setPostTitle("");
            setContent("");
            setShowTitle(false);
            setTags([]);
            setFiles([]);
            setPostId(null);
            setMentionLabel('cc');
            setMentionedMembers([]);
            setIsPostCreation(true);
            setIsScheduledPost(false);
            setScheduledAt(null);
            setPollOptions(['', '']);
            setQuestion('');
            setShowPollContainer(false);
            setShowMentionContainer(false);
            setPurpose('CREATE');
        }

        const quillEditor = document.querySelector('.ql-container.ql-snow');
        const quillToolbar = document.querySelector('.ql-toolbar.ql-snow');
        const fullscreenQuillEditor = document.querySelector('.fullscreen-post-creation .ql-editor');
        const inputWrapper = document.getElementById('post-content-input-wrapper');
        const createPostTags = document.getElementById('create-post-tags');
        const postInput = document.getElementById('post-input');
        const postTitle = document.getElementById('post-title-input');
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
            postInput.style.height = `calc(100% - ${createPostTags.offsetHeight}px - ${postTitle ? postTitle.offsetHeight + 20 : 0}px)`;
        }
        if (fullscreenQuillEditor) {
            // fullscreenQuillEditor.style.border = '1px solid orange';
            fullscreenQuillEditor.style.height = `calc(100% - 40px - ${postTitle ? postTitle.offsetHeight + 20 : 0}px)`;
        }
        if (inputWrapper) {
            inputWrapper.style.height = `calc(520px - ${postAttachments ? postAttachments.offsetHeight : 0}px - ${postTitle ? postTitle.offsetHeight + 40 : 0}px)`;
        }

    }, [currentPostData, isShowCreatePostModal]);

    const postCreation = (api, method, options) => {

        dispatch(showLoader());

        Media.uploadMultipleMedia(files, setFiles).then(() => {

            const payload = {
                "title": postTitle,
                "description": content
            };

            if (tags.length) {
                Object.assign(payload, { tags });
            }

            if (files.length) {
                Object.assign(payload, {
                    "mediaIds": files.map(file => {
                        const { mediaId, extension } = file || {};
                        return {
                            'id': mediaId, extension
                        }
                    })
                });
            }

            if (question.trim().length) {
                Object.assign(payload, {
                    "poll": {
                        "question": question,
                        "options": pollOptions
                    }
                });
            }

            if (mentionedMembers.length) {
                Object.assign(payload, {
                    "mentions": {
                        "label": mentionLabel,
                        "data": mentionedMembers.map(({ id }) => id)
                    }
                });
            }

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

    const afterValidation = (callback) => {

        if (content.trim().length == 0) {
            return dispatch(showPopup({ message: "Post content cannot be empty!", type: 'error' }));
        }

        const hasQuestion = question.trim().length > 0;
        const hasOptions = pollOptions.some(option => option.trim().length > 0);

        if (hasQuestion || hasOptions) {
            if (question.trim().length == 0) {
                return dispatch(showPopup({ message: "Poll question cannot be empty!", type: 'error' }));
            }
            for (let option of pollOptions) {
                if (option.trim().length == 0) {
                    return dispatch(showPopup({ message: "Poll options cannot be empty!", type: 'error' }));
                }
            }
        }

        callback();
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

    const shareClickHandler = () => {
        afterValidation(() => {
            dispatch(showConfirmationPopup({
                message: 'Are you sure you want to post this?',
                options: [
                    {
                        title: 'Post',
                        color: 'green',
                        onClick: handlePostSubmit
                    },
                    {
                        title: 'Cancel',
                        color: 'gray',
                        onClick: () => dispatch(hideConfirmationPopup())
                    }
                ]
            }));
        });
    }

    const scheduleClickHandler = (scheduledAt) => {
        afterValidation(() => {
            dispatch(showConfirmationPopup({
                message: 'Are you sure you want to schedule this post?',
                options: [
                    {
                        title: 'Schedule',
                        color: 'green',
                        onClick: () => handlePostSchedule(scheduledAt)
                    },
                    {
                        title: 'Cancel',
                        color: 'gray',
                        onClick: () => dispatch(hideConfirmationPopup())
                    }
                ]
            }));
        });
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
                        {showTitle && <input type='text' placeholder='Title' id='post-title-input' autoComplete='off' value={postTitle} onChange={(e) => setPostTitle(e.target.value)} />}
                        <div className='FCSS w100' id='post-content-input-wrapper'>
                            <ReactQuill theme="snow" value={content} onChange={setContent} className='w100' id='post-input' placeholder='What do you want to share?' />
                            <PostTagContainer tags={tags} setTags={setTags} />
                        </div>
                        {purpose == 'CREATE' && showPollContainer && <PostPoll question={question} setQuestion={setQuestion} options={pollOptions} setOptions={setPollOptions} />}
                        <PostAttachments files={files} removeSelectedFileHandler={removeSelectedFileHandler} />
                        {showMentionContainer && <PostMentions mentionLabel={mentionLabel} mentionedMembers={mentionedMembers} setMentionLabel={setMentionLabel} setMentionedMembers={setMentionedMembers} />}
                    </div>
                </div>

                <div className='w100 FRCB post-footer'>
                    <div className='FRCS post-actions'>
                        <PostAction icon='fa-solid fa-t' label='Title' onClick={() => { setShowTitle(true); setPostTitle(''); utils.autoFocusInput('post-title-input'); }} />
                        {purpose == 'CREATE' && <PostAction icon='fa-solid fa-square-poll-vertical' label='Poll' onClick={() => { setShowPollContainer(true); utils.autoFocusInput('poll-question-input'); }} />}
                        <PostAction icon='fa-solid fa-user-tag' label='Mention' onClick={showMentionContainerHandler} />
                        <PostAction icon='fa-regular fa-image' label='Attachment' onClick={() => fileInputRef.current.click()} />
                        <input type='file' multiple style={{ display: 'none' }} ref={fileInputRef} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} />
                    </div>
                    <div className='FRCE'>
                        <button id='cancel-post-button' onClick={onMinimize}>Cancel</button>
                        <ShareWithSchedule scheduleClickHandler={scheduleClickHandler} shareClickHandler={shareClickHandler} scheduledAt={scheduledAt} />
                    </div>
                </div>

            </div>
        </div>
    );
};


export default CreateNewPost;
