import './Post.css'
import Poll from './Poll';
import utils from '../../../utils';
import PostOptions from './PostOptions';
import PostsLikedBy from './PostsLikedBy';
import { NavLink } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request';
import PostCommentsLayout from './PostCommentsLayout';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { setActivePostId } from '../../../store/reducers/PostSlice';
import { showFullScreenImage } from '../../../store/reducers/FullScreenImageSlice';

const PostDescription = ({ html }) => <div className="post-description html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />

const PostHeader = ({ userId, displayName, displayPicture, createdAt, postId, commentable, setCommentable, isCreator, poll }) => {

    const dispatch = useDispatch();
    const [showLikesList, setShowLikesList] = useState(false);
    const { activePostId } = useSelector(state => state.posts); // only one can be open
    const [pollUpdatable, setPollUpdatable] = useState(poll?.updatable);

    const [isOptionOpen, setIsOptionOpen] = useState(false);

    const onToggleOption = () => {
        dispatch(setActivePostId(postId));
    }

    useEffect(() => {
        setIsOptionOpen(activePostId === postId);
    }, [activePostId, postId]);

    return <div className='PostHeader FRCB'>
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_40_40 user_photo' onClick={() => { dispatch(showProfile(userId)); }} />
            <div className='FCSS'>
                <h3 className='user_name'>{displayName}</h3>
                <p className='created_at' title={utils.convertDateAndTime(createdAt)}>{utils.agoTimeCalculator(createdAt)}</p>
            </div>
        </div>
        <i className='fa-solid fa-ellipsis cursP' onClick={onToggleOption}></i>
        {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} showLikesList={showLikesList} />}
        {isOptionOpen && <PostOptions pollUpdatable={pollUpdatable} setPollUpdatable={setPollUpdatable} postId={postId} commentable={commentable} setCommentable={setCommentable} isCreator={isCreator} poll={poll} setShowLikesList={setShowLikesList} />}
    </div>
}

const PostBody = ({ title, description, media, toggleMaxHeight, tags, poll, setPoll }) => {
    return <div className='PostBody FCSS'>
        {title ? <h4 className='postTitle'>{title}</h4> : null}
        <PostDescription html={description} />
        <TagsContainer tags={tags} />
        {poll && <Poll poll={poll} setPoll={setPoll} />}
        {media ? <MediaContainer media={media} toggleMaxHeight={toggleMaxHeight} key={media.id} /> : null}
    </div>
}

const TagsContainer = ({ tags }) => {
    return <div className='FRCS tagsContainer'>
        {tags && tags.map((tag, index) => (
            <NavLink to={`/feeds/tag/${tag.replace(/^#/m, '')}`} key={index} className='tagNavLink'>{tag}</NavLink>
        ))}
    </div>
}

const MediaContainer = ({ media, toggleMaxHeight }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (mediaItem) => {
        dispatch(showFullScreenImage(mediaItem));
    }

    return <div className='mediaContainer FCSS w100' onClick={toggleMaxHeight}>
        {media.map((mediaItem, index) => <img key={index} src={mediaItem} className='media_image w100' onClick={() => showFullScreenImageHandler(mediaItem)} />)}
    </div>
}

const Comment = ({ postId, commentable, commentsCount, setCommentsCount }) => {

    const [showCommentsList, setShowCommentsList] = useState(false);

    const closeShowCommentsList = (e) => {
        if (e.target.id === 'post-comments-layout') {
            setShowCommentsList(false);
        }
    }

    return <>
        {commentable && <div className='postActions leftFooter FRCC mY5' onClick={() => setShowCommentsList(true)}><i className="fa-regular fa-comment"></i> {commentsCount}</div>}
        {showCommentsList && <PostCommentsLayout postId={postId} setCommentsCount={setCommentsCount} closeShowCommentsList={closeShowCommentsList} commentable={commentable} />}
    </>
}

const PostFooter = ({ postId, likedPost, likesCount, commentsCount, setCommentsCount, commentable, bookmarkable, bookmarked, likeHandler }) => {

    return <div className='PostFooter w100 FRCB'>
        <div className='FRCS'>
            <div className={`postActions leftFooter FRCC mY5 ${likedPost && 'post-liked'}`} onClick={likeHandler}><i className={`fa-regular fa-thumbs-up`}></i> {likesCount}</div>
            <Comment postId={postId} commentable={commentable} commentsCount={commentsCount} setCommentsCount={setCommentsCount} />
        </div>
        <div className='FRCE'>
            {bookmarkable && <div className='postActions rightFooter FRCC mY5'><i className={`fa-regular fa-bookmark ${bookmarked && 'bookmarked'}`}></i></div>}
        </div>
        {/* <p>{mentions} Mentions</p> */}
    </div>
}

const Post = ({ post }) => {

    const dispatch = useDispatch();

    const { id, title, owner, description, createdAt, impression, media, mentions, liked, bookmarked, tags, bookmarkable, creator: isCreator } = post,
        { likes, comments } = impression || {},
        { userId, displayName, displayPicture } = owner,
        toggleMaxHeight = function (e) {
            const { target } = e;
            try {
                if (target.classList.contains('media_image')) {
                    const mediaContainer = target.parentNode;
                    if (mediaContainer.style.maxHeight === '100%') {
                        dispatch(showFullScreenImage(target.src));
                    } else {
                        mediaContainer.style.maxHeight = '100%';
                    }
                }
            } catch (error) {
                console.error({ 'module': toggleMaxHeight, error });  //eslint-disable-line no-console
            }
        };

    const [commentable, setCommentable] = useState(post.commentable);
    const [likedPost, setLikedPost] = useState(liked);
    const [likesCount, setLikesCount] = useState(likes || 0);
    const [commentsCount, setCommentsCount] = useState(comments || 0);
    const [poll, setPoll] = useState(post.poll);

    const likeHandler = () => {

        apiRequest(`/api/v1/post/like/${id}`, likedPost ? 'DELETE' : 'POST').then(() => {
            setLikedPost(!likedPost);
            if (likedPost) {
                setLikesCount(likesCount - 1);
            } else {
                setLikesCount(likesCount + 1);
            }
        }).catch(({ message }) => {
            showPopup({ message, type: 'error' });
        });
    }

    return (
        <div className='Post w100'>
            <PostHeader userId={userId} postId={id} displayName={displayName} displayPicture={displayPicture} createdAt={createdAt} commentable={commentable} setCommentable={setCommentable} isCreator={isCreator} poll={poll} />
            <PostBody title={title} description={description} media={media} toggleMaxHeight={toggleMaxHeight} tags={tags} poll={poll} setPoll={setPoll} />
            <PostFooter postId={id} likedPost={likedPost} likesCount={likesCount} commentsCount={commentsCount} setCommentsCount={setCommentsCount} commentable={commentable} bookmarkable={bookmarkable} bookmarked={bookmarked} likeHandler={likeHandler} />
        </div>
    )
}

export default Post