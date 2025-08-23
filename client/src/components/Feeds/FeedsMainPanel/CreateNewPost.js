import './CreateNewPost.css';
import Media from '../../../Media';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useEffect, useRef, useState } from 'react';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showFullScreenImage } from '../../../store/reducers/FullScreenImageSlice';

const ShareWithSchedule = ({ onShare, onSchedule }) => {

    const [showSchedule, setShowSchedule] = useState(false);

    return (
        <div className="share-wrapper FCSS w100">
            <div className="FRCC w100" id='share-post-button-wrapper'>
                <button className="share-main" onClick={onShare}>Share</button>
                <i className={`fa ${showSchedule ? 'fa-chevron-up' : 'fa-chevron-down'}`} id="share-chevron" onClick={() => setShowSchedule(prev => !prev)}></i>
            </div>

            <div className={`w100 schedule-slide ${showSchedule ? 'slide-visible' : ''}`}>
                <button className="schedule-btn" onClick={onSchedule}>Schedule</button>
            </div>
        </div>
    );
};

const CreateNewPost = ({ onMinimize }) => {

    const dispatch = useDispatch();
    const fileInputRef = useRef(null);
    const [tags, setTags] = useState(["#firstpost", "#virat"]);
    const [files, setFiles] = useState([]);
    const [content, setContent] = useState("This is my first post");
    const [postTitle, setPostTitle] = useState("My first post with images");
    const myProfile = useSelector(state => state.myProfile);

    const allowedTypes = ['image/png', 'image/jpeg', 'image/gif'];

    useEffect(() => {

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
    }, []);

    const handlePostSubmit = (e) => {

        e.preventDefault();
        e.stopPropagation();

        if (content.trim().length == 0) {
            alert("Post content cannot be empty!");
            return;
        }

        Media.uploadMultipleMedia(files, setFiles).then(() => {

            apiRequest(`/api/v1/post/create`, 'POST', {
                "title": postTitle,
                "description": content,
                "tags": tags,
                "mediaIds": files.map(({ mediaId, extension, file }) => {
                    return {
                        'id': mediaId, extension
                    }
                }),
                // "poll": {
                //     "question": "Which company are you targeting?",
                //     "options": [
                //         "Microsoft", "PayPal", "Google", "Amazon"
                //     ]
                // }
            }).then(({ data, message }) => {
                onMinimize();
                dispatch(showPopup({ message, type: 'success' }));
            }).catch(({ message }) => {
                dispatch(showPopup({ message, type: 'error' }));
            });

        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });

    };

    const handlePostSchedule = () => {
        console.log(content);
    };

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
            preview: file.type.startsWith("image")
                ? URL.createObjectURL(file)
                : null
        }));

        setFiles((prev) => [...prev, ...withPreview]);
        e.target.value = ""; // Reset file input
    };

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

    const removeSelectedFileHandler = (file) => {
        setFiles(files.filter(f => f.name !== file.name));
    }

    const removeTagHandler = (e) => {
        const tagToRemove = e.target.previousSibling.textContent;
        setTags(tags.filter(tag => tag !== tagToRemove));
    }

    return (
        <div id='create-new-post-overlay' className='entire-screen-overlay fullscreen-post-creation FRCC'>
            <div id='CreateNewPost' className='FCSS post-container'>
                <div className='FRCB w100'>
                    <label className='post-label'>Post Something</label>
                    {/* <i className="fa-solid fa-down-left-and-up-right-to-center" id='expand-post-creation' onClick={onMinimize}></i> */}
                </div>

                <div className='FRSS w100' id='post-input-section'>
                    <img src={myProfile?.displayPicture} className='img_40_40 user-avatar' alt='User' />
                    <div className='FCSS w100' id='post-input-wrapper'>
                        <input type='text' placeholder='Title' id='post-title-input' autoComplete='off' value={postTitle} onChange={(e) => setPostTitle(e.target.value)} />
                        <div className='FCSS w100' id='post-content-input-wrapper'>
                            <ReactQuill theme="snow" value={content} onChange={setContent} className='w100' id='post-input' placeholder='What do you want to share?' />
                            <div className='FRCS' id='create-post-tags'>
                                {tags.map((tag, index) => (
                                    <div className='create-post-tag FRCC' key={index}>
                                        <span>{tag}</span>
                                        <i className="fa-solid fa-xmark" onClick={removeTagHandler}></i>
                                    </div>
                                ))}
                                <input type='text' placeholder='Add Tag' autoComplete='off' id='create-tag-input' onKeyDown={addTagHandler} />
                            </div>
                        </div>
                        {files.length > 0 && <div id='post-attachments' className='FRCS w100'>
                            {files.map((file, index) => (
                                <FilePreview key={index} file={file} removeSelectedFileHandler={removeSelectedFileHandler} />
                            ))}
                        </div>}
                    </div>
                </div>

                <div className='w100 FRCB post-footer'>
                    <div className='FRCS post-actions'>
                        <PostAction icon='fa-solid fa-square-poll-vertical' label='Poll' />
                        <PostAction icon='fa-regular fa-image' label='Attachment' onClick={() => fileInputRef.current.click()} />
                        <input type='file' multiple style={{ display: 'none' }} ref={fileInputRef} onChange={handleFileChange} accept={allowedTypes.length ? allowedTypes.join(",") : "*/*"} />
                        {/* <PostAction icon='fa-regular fa-hashtag' label='Tag' /> */}
                        {/* <PostAction icon='fa-regular fa-at' label='Mention' /> */}
                        {/* <PostAction icon='fa-solid fa-t' label='Title' /> */}
                        {/* <PostAction icon='fa-regular fa-calendar-days' label='Schedule' /> */}
                    </div>
                    <div className='FRCE'>
                        <button id='cancel-post-button' onClick={onMinimize}>Cancel</button>
                        <ShareWithSchedule onShare={handlePostSubmit} onSchedule={handlePostSchedule} />
                    </div>
                </div>

            </div>
        </div>
    );
};

const FilePreview = ({ file, removeSelectedFileHandler }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = () => {
        dispatch(showFullScreenImage(file.preview));
    }

    return <div className='post-attachment-preview FRCC' key={file.name}>
        <img src={file.preview} alt={file.name} className='post-attachment-image' onClick={showFullScreenImageHandler} />
        <i className="fa-solid fa-xmark post-attachment-remove" onClick={() => removeSelectedFileHandler(file)}></i>
    </div>
}

const PostAction = ({ icon, label, onClick }) => (
    <span className='actions_post_creation FRCC' onClick={onClick ? onClick : null}>
        <i className={`${icon} mR5`}></i>
        <span>{label}</span>
    </span>
);

export default CreateNewPost;
