import './CreateNewPost.css';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useState, useRef, useEffect } from 'react';
import { apiRequest } from '../../../http_request';

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

const CreateNewPost = () => {

    const [content, setContent] = useState("");
    const [isFullScreen, setIsFullScreen] = useState(false);

    const handlePostSubmit = () => {

        if (content.trim().length == 0) {
            alert("Post content cannot be empty!");
            return;
        }

        apiRequest(`/api/v1/post/create`, 'POST', {
            "title": "New Post",
            "description": content,
            "tags": [
                "#new_post"
            ],
            "mediaIds": [

            ],
            "poll": {
                "question": "Which company are you targeting?",
                "options": [
                    "Microsoft", "PayPal", "Google", "Amazon"
                ]
            }
        }).then(({ data }) => {
            setIsFullScreen(false);
            content("");
        }).catch(({ message }) => {

        });
    };

    const handlePostSchedule = () => {
        console.log(content);
    };

    const fullScreenPostCreationHandler = () => {
        setIsFullScreen(!isFullScreen);
    }

    return (
        <div id='create-new-post-overlay' className={isFullScreen ? "fullscreen-post-creation" : ""}>
            <div id='CreateNewPost' className='FCSS post-container'>
                <div className='FRCB w100'>
                    <label className='post-label'>Post Something</label>
                    <i className="fa-solid fa-up-right-and-down-left-from-center" id='expand-post-creation' onClick={fullScreenPostCreationHandler}></i>
                </div>

                <div className='FRSS w100 post-input-section'>
                    <img src='/images/Users/user_10.jpg' className='img_40_40 user-avatar' alt='User' />
                    <ReactQuill theme="snow" value={content} onChange={setContent} className='w100 post-input' placeholder='What do you want to share?' />
                </div>

                <div className='w100 FRCB post-footer'>
                    <div className='FRCS post-actions'>
                        <PostAction icon='fa-regular fa-image' label='Attachment' />
                        <PostAction icon='fa-regular fa-hashtag' label='Tag' />
                        {/* <PostAction icon='fa-regular fa-at' label='Mention' /> */}
                        <PostAction icon='fa-solid fa-t' label='Title' />
                        <PostAction icon='fa-solid fa-square-poll-vertical' label='Poll' />
                        <PostAction icon='fa-regular fa-calendar-days' label='Schedule' />
                    </div>
                    <div className='FRCE'>
                        <ShareWithSchedule onShare={handlePostSubmit} onSchedule={handlePostSchedule} />
                    </div>
                </div>

            </div>
        </div>
    );
};

const PostAction = ({ icon, label }) => (
    <span className='actions_post_creation FRCC'>
        <i className={`${icon} mR5`}></i>
        <span>{label}</span>
    </span>
);

export default CreateNewPost;
