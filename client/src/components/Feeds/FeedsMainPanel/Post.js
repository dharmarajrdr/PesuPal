import utils from '../../../utils';
import Profile from '../../OthersProfile/Profile';
import './Post.css'
import { useState } from 'react';

const serverDomain = 'http://localhost:8080';

const PostDescription = ({ html }) => <div className="post-description postContent" dangerouslySetInnerHTML={{ __html: html }} />

const Post = ({ post }) => {

    const { title, owner, description, createdAt, impression, media, mentions, tags, commentable, bookmarkable } = post,
        { likes, comments } = impression || {},
        { displayName, displayPicture } = owner,
        [fullScreenImage, setFullScreenImage] = useState(null),
        convertDateAndTime = function (str) {
            try {
                const toTwoDigits = function (str) {
                    str = str + '';
                    return str.length == 2 ? str : '0' + str;
                }
                let d = new Date(str);
                if (d) {
                    let hours = d.getHours();
                    let am_pm = hours >= 12 ? 'PM' : 'AM';
                    hours = hours > 12 ? hours - 12 : hours;
                    return toTwoDigits(d.getDate()) + "/" + toTwoDigits(d.getMonth() + 1) + "/" + d.getFullYear() + " " + toTwoDigits(hours) + ":" + toTwoDigits(d.getMinutes()) + " " + am_pm;
                }
            } catch (error) {
                console.error({ 'module': convertDateAndTime, error, str });  //eslint-disable-line no-console
            }
        }, toggleMaxHeight = function (e) {
            const { target } = e;
            try {
                if (target.classList.contains('media_image')) {
                    const mediaContainer = target.parentNode;
                    if (mediaContainer.style.maxHeight === '100%') {
                        setFullScreenImage(target.src)
                    } else {
                        mediaContainer.style.maxHeight = '100%';
                    }
                }
            } catch (error) {
                console.error({ 'module': toggleMaxHeight, error });  //eslint-disable-line no-console
            }
        }, closeFullScreen = function () {
            setFullScreenImage(null);
        };

    const [showProfile, setShowProfile] = useState(false);

    return (
        <div className='Post w100'>
            {fullScreenImage ?
                <div id='view_image_full_screen' className='FRCC'>
                    <div id='closeFullScreen' className='FRCC' onClick={closeFullScreen}>
                        <span className='mR5'>Close</span>
                        <i className="fa-solid fa-xmark"></i>
                    </div>
                    <img src={fullScreenImage} />
                </div> : null}
            <div className='PostHeader FRCB'>
                <div className='FRCS'>
                    <img src={displayPicture} alt={displayName} className='img_40_40 user_photo' onClick={() => setShowProfile(true)} />
                    <div className='FCSS'>
                        <h3 className='user_name'>{displayName}</h3>
                        <p className='created_at' title={convertDateAndTime(createdAt)}>{utils.agoTimeCalculator(createdAt)}</p>
                    </div>
                </div>
                <i className='fa-solid fa-ellipsis'></i>
            </div>
            <div className='PostBody FCSS'>
                {title ? <h4 className='postTitle'>{title}</h4> : null}
                <PostDescription html={description} />
                {media ?
                    <div className='mediaContainer FCSS w100' onClick={toggleMaxHeight}>
                        {media.map((media, index) => <img key={index} src={`${serverDomain}/api/v1/media/${media}`} className='media_image w100' />)}
                    </div> : null}
            </div>
            <div className='PostFooter w100 FRCB'>
                <div className='FRCS'>
                    <div className='postActions leftFooter FRCC mY5'><i className="fa-regular fa-thumbs-up"></i> {likes}</div>
                    {commentable && <div className='postActions leftFooter FRCC mY5'><i className="fa-regular fa-comment"></i> {comments}</div>}
                </div>
                <div className='FRCE'>
                    {bookmarkable && <div className='postActions rightFooter FRCC mY5'><i className="fa-regular fa-bookmark"></i></div>}
                </div>
                {/* <p>{mentions} Mentions</p>
                <p>{tags} Tags</p> */}
            </div>
            {showProfile && <Profile Profile={owner} setShowProfile={setShowProfile} />}
        </div>
    )
}

export default Post