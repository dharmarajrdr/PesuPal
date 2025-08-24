import { useState } from 'react'
import { useDispatch } from 'react-redux';
import { apiRequest } from '../../../http_request';
import PostCommentsLayout from './PostCommentsLayout';
import { showPopup } from '../../../store/reducers/PopupSlice';

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

    const dispatch = useDispatch();
    const [likedPost, setLikedPost] = useState(liked);
    const [likesCount, setLikesCount] = useState(likes || 0);

    const likeHandler = () => {

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

    return <div className='PostFooter w100 FRCB'>
        <div className='FRCS'>
            <div className={`postActions leftFooter FRCC mY5 ${likedPost && 'post-liked'}`} onClick={likeHandler}><i className={`fa-regular fa-thumbs-up`}></i> {likesCount}</div>
            <Comment postId={postId} commentable={commentable} comments={comments} />
        </div>
        <div className='FRCE'>
            {bookmarkable && <div className='postActions rightFooter FRCC mY5'><i className={`fa-regular fa-bookmark ${bookmarked && 'bookmarked'}`}></i></div>}
        </div>
        {/* <p>{mentions} Mentions</p> */}
    </div>
}

export default PostFooter