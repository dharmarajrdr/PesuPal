import { NavLink } from 'react-router-dom';
import utils from '../../../utils';
import Profile from '../../OthersProfile/Profile';
import './Post.css'
import { useState } from 'react';
import { apiRequest } from '../../../http_request';
import { UsePopupFromSession } from '../../../UsePopupFromSession';
import Popup from '../../Popup';
import PostOptions from './PostOptions';

const PostDescription = ({ html }) => <div className="post-description postContent" dangerouslySetInnerHTML={{ __html: html }} />

const PostHeader = ({ displayName, displayPicture, createdAt, setShowProfile, postId, isOptionOpen, onToggleOption }) => {

    return <div className='PostHeader FRCB'>
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_40_40 user_photo' onClick={() => setShowProfile(true)} />
            <div className='FCSS'>
                <h3 className='user_name'>{displayName}</h3>
                <p className='created_at' title={utils.convertDateAndTime(createdAt)}>{utils.agoTimeCalculator(createdAt)}</p>
            </div>
        </div>
        <i className='fa-solid fa-ellipsis cursP' onClick={onToggleOption}></i>
        {isOptionOpen && <PostOptions postId={postId} />}
    </div>
}

const PostBody = ({ title, description, media, toggleMaxHeight, tags }) => {
    return <div className='PostBody FCSS'>
        {title ? <h4 className='postTitle'>{title}</h4> : null}
        <PostDescription html={description} />
        <TagsContainer tags={tags} />
        {media ? <MediaContainer media={media} toggleMaxHeight={toggleMaxHeight} key={media.id} /> : null}
    </div>
}

const TagsContainer = ({ tags }) => {
    return <div className='FRCS tagsContainer'>
        {tags && tags.map((tag, index) => (
            <NavLink to={`/feeds/tag/${tag}`} key={index} className='tagNavLink'>{tag}</NavLink>
        ))}
    </div>
}

const MediaContainer = ({ media, toggleMaxHeight }) => {
    return <div className='mediaContainer FCSS w100' onClick={toggleMaxHeight}>
        {media.map((media, index) => <img key={index} src={`${utils.serverDomain}/api/v1/media/${media}`} className='media_image w100' />)}
    </div>
}

const FullScreenImage = ({ closeFullScreen, fullScreenImage }) => {
    return <div id='view_image_full_screen' className='FRCC'>
        <div id='closeFullScreen' className='FRCC' onClick={closeFullScreen}>
            <span className='mR5'>Close</span>
            <i className="fa-solid fa-xmark"></i>
        </div>
        <img src={fullScreenImage} />
    </div>
}

const PostFooter = ({ likedPost, likesCount, comments, commentable, bookmarkable, bookmarked, likeHandler }) => {
    return <div className='PostFooter w100 FRCB'>
        <div className='FRCS'>
            <div className={`postActions leftFooter FRCC mY5 ${likedPost && 'post-liked'}`} onClick={likeHandler}><i className={`fa-regular fa-thumbs-up`}></i> {likesCount}</div>
            {commentable && <div className='postActions leftFooter FRCC mY5'><i className="fa-regular fa-comment"></i> {comments}</div>}
        </div>
        <div className='FRCE'>
            {bookmarkable && <div className='postActions rightFooter FRCC mY5'><i className={`fa-regular fa-bookmark ${bookmarked && 'bookmarked'}`}></i></div>}
        </div>
        {/* <p>{mentions} Mentions</p> */}
    </div>
}

const Post = ({ post, isOptionOpen, onToggleOption }) => {

    const { id, title, owner, description, createdAt, impression, media, mentions, liked, bookmarked, tags, commentable, bookmarkable } = post,
        { likes, comments } = impression || {},
        { userId, displayName, displayPicture } = owner,
        [fullScreenImage, setFullScreenImage] = useState(null),
        toggleMaxHeight = function (e) {
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

    const [popupData, setPopupData] = useState(null);
    const [likedPost, setLikedPost] = useState(liked);
    const [likesCount, setLikesCount] = useState(likes || 0);

    const showPopup = (message, type) => {
        setPopupData({ message, type });
    };

    UsePopupFromSession(showPopup);

    const likeHandler = () => {

        apiRequest(`/api/v1/post/like/${id}`, likedPost ? 'DELETE' : 'POST').then(() => {
            setLikedPost(!likedPost);
            if (likedPost) {
                setLikesCount(likesCount - 1);
            } else {
                setLikesCount(likesCount + 1);
            }
        }).catch(({ message }) => {
            showPopup(message, 'error');
        });
    }

    return (
        <div className='Post w100'>
            {popupData && <Popup message={popupData.message} type={popupData.type} />}
            {fullScreenImage ? <FullScreenImage closeFullScreen={closeFullScreen} fullScreenImage={fullScreenImage} /> : null}
            <PostHeader isOptionOpen={isOptionOpen} onToggleOption={onToggleOption} postId={id} displayName={displayName} displayPicture={displayPicture} createdAt={createdAt} setShowProfile={setShowProfile} />
            <PostBody title={title} description={description} media={media} toggleMaxHeight={toggleMaxHeight} tags={tags} />
            <PostFooter likedPost={likedPost} likesCount={likesCount} comments={comments || 0} commentable={commentable} bookmarkable={bookmarkable} bookmarked={bookmarked} likeHandler={likeHandler} />
            {showProfile && <Profile userId={userId} setShowProfile={setShowProfile} />}
        </div>
    )
}

export default Post