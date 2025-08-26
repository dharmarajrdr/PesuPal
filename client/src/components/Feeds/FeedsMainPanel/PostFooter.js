import { useState } from 'react'
import { useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { apiRequest } from '../../../http_request';
import PostCommentsLayout from './PostCommentsLayout';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { deletePost } from '../../../store/reducers/PostSlice';

const Comment = ({ postId, commentable, comments }) => {

    const [showCommentsList, setShowCommentsList] = useState(false);
    const [commentsCount, setCommentsCount] = useState(comments || 0);

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

const PostFooter = ({ post, commentable, bookmarkable, bookmarked }) => {

    const { id: postId, liked, impression } = post || {};
    const { likes, comments } = impression || {};

    const { nav } = useParams();
    const dispatch = useDispatch();
    const [likedPost, setLikedPost] = useState(liked);
    const [likesCount, setLikesCount] = useState(likes || 0);

    const [bookmarkedPost, setBookmarkedPost] = useState(bookmarked);

    const likeHandler = (e) => {

        e.stopPropagation();
        e.preventDefault();

        apiRequest(`/api/v1/post/like/${postId}`, likedPost ? 'DELETE' : 'POST').then(() => {
            setLikedPost(!likedPost);
            if (likedPost) {
                setLikesCount(likesCount - 1);
            } else {
                setLikesCount(likesCount + 1);
            }
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const bookmarkHandler = (e) => {

        e.stopPropagation();
        e.preventDefault();

        apiRequest(`/api/v1/post/bookmark/${postId}`, bookmarkedPost ? 'DELETE' : 'POST').then(() => {
            if (nav === 'bookmarks' && bookmarkedPost) {
                dispatch(deletePost(postId));
            }
            setBookmarkedPost(!bookmarkedPost);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return <div className='PostFooter w100 FRCB'>
        <div className='FRCS'>
            <div className={`postActions leftFooter FRCC mY5 ${likedPost && 'post-liked'}`} onClick={likeHandler}><i className={`fa-regular fa-thumbs-up`}></i> {likesCount}</div>
            <Comment postId={postId} commentable={commentable} comments={comments} />
        </div>
        <div className='FRCE'>
            {bookmarkable && <div className={`postActions rightFooter FRCC mY5 ${bookmarkedPost && 'post-bookmarked'}`} onClick={bookmarkHandler}><i className={`fa-${bookmarkedPost ? 'solid' : 'regular'} fa-bookmark`}></i></div>}
        </div>
    </div>
}

export default PostFooter