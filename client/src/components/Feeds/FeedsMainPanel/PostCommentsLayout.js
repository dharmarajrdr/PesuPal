import { useEffect, useState } from 'react';
import './PostCommentsLayout.css';
import { apiRequest } from '../../../http_request';
import Loader from '../../Loader';
import ErrorMessage from '../../ErrorMessage';
import utils from '../../../utils';
import Profile from '../../OthersProfile/Profile';
import ConfirmationPopup from '../../Utils/ConfirmationPopup';

const NoCommentsFound = () => {
    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-comment-slash' /> No comments found
            </p>
            <p className='w100 alignCenter'>Create a comment to start the discussion</p>
        </div>
    )
}

const CreateCommentContainer = ({ postId, setComments, setCommentsCount }) => {

    const [comment, setComment] = useState('');

    const submitCommentHandler = () => {
        if (!comment.trim()) { return; }
        apiRequest(`/api/v1/post/comment`, "POST", { message: comment, postId }).then(({ data }) => {
            setComment('');
            setComments(prevComments => [data, ...prevComments]);
            setCommentsCount(prevCount => prevCount + 1);
        }).catch(({ message }) => {
            console.error('Error creating comment:', message);
        });
    }

    return (
        <div id='create-comment' className='FRCC w100'>
            <textarea className='create-comment-textarea' placeholder='Write a comment...' value={comment} onChange={(e) => setComment(e.target.value)} />
            <button className='create-comment-button' onClick={submitCommentHandler}>
                <i className='fa fa-paper-plane mR5' /> Post
            </button>
        </div>
    );
};


const CommentContent = ({ html }) => <div className="comment-content html-content-renderer" dangerouslySetInnerHTML={{ __html: html }} />

const Comment = ({ comment, setComments, setCommentsCount }) => {

    const { id, userId, displayName, displayPicture, message, createdAt, replyCount, deletable } = comment;

    const [showReplies, setShowReplies] = useState(false);
    const [showProfile, setShowProfile] = useState(false);
    const [showDeleteIcon, setShowDeleteIcon] = useState(false);
    const [clickedDelete, setClickedDelete] = useState(false);

    const deleteCommentHandler = () => {
        apiRequest(`/api/v1/post/comment/${id}`, 'DELETE').then(() => {
            setComments(prevComments => prevComments.filter(c => c.id !== id));
            setCommentsCount(prevCount => prevCount - 1);
        }).catch(({ message }) => {
            console.error('Error deleting comment:', message);
        });
    }

    const deletePopupOptions = [{
        "title": "Yes",
        "color": "#ff4d4d",
        "onClick": deleteCommentHandler
    }, {
        "title": "No",
        "color": "#4CAF50",
    }];

    return (
        <div className='comment-item FRSS w100' onMouseEnter={() => setShowDeleteIcon(true)} onMouseLeave={() => setShowDeleteIcon(false)}>
            <div className='FCCS'>
                <img src={displayPicture} alt={displayName} className='comment-user-picture img_40_40 mR10' onClick={() => setShowProfile(true)} />
                {showProfile && <Profile userId={userId} setShowProfile={() => { setShowProfile(false) }} />}
            </div>
            <div className='FCSS comment-content-container'>
                <div className='comment-user FCSS w100'>
                    <div className='FRCB w100'>
                        <h6 className='comment-user-name'>{displayName}</h6>
                        <span className='comment-date fs10 color777'>{utils.convertDateAndTime(createdAt)}</span>
                    </div>
                    <CommentContent html={message} />
                </div>
                <div className='comment-footer FRCB w100 mT10'>
                    <div>
                        <span className='fs12 cursP add-new-reply color555'><i className='fa fa-reply mR5 fs10 color777' />Reply</span>
                        {replyCount > 0 && <>
                            <span className='comment-reply-toggle-button fs12 cursP mL10 pL10' onClick={() => setShowReplies(!showReplies)}>
                                {/* {showReplies ? <i className='fa fa-chevron-up mR5' /> : <i className='fa fa-chevron-right mR5' />} */}
                                {showReplies ? 'Hide' : 'Show'} {replyCount} replies
                            </span>
                            {showReplies && <CommentReply commentId={id} />}
                        </>}
                    </div>
                    {deletable && showDeleteIcon && <p className='fs12 cursP delete-comment color555' onClick={() => setClickedDelete(true)}><i className='fa fa-trash mR5 fs10 color777' />Delete</p>}
                    {clickedDelete && <ConfirmationPopup message={"Are you sure you want to delete this comment?"} onClose={() => setClickedDelete(false)} options={deletePopupOptions} />}
                </div>
            </div>
        </div>
    )
}

const CommentsList = ({ comments, setComments, setCommentsCount }) => {
    return <>
        {comments.map((comment, index) => <Comment key={index} comment={comment} setComments={setComments} setCommentsCount={setCommentsCount} />)}
    </>
}

const CommentReply = ({ commentId }) => {
    return null;
}

const CommentsContainer = ({ postId, commentable, setCommentsCount }) => {

    const [comments, setComments] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        apiRequest(`/api/v1/post/${postId}/comment`, "GET").then(({ data }) => {
            setLoading(false);
            setComments(data);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });
    }, [postId]);

    return (
        <div id='comments-container' className='FCCS mT20 centerMe'>
            {
                loading ? <Loader />
                    : error ? <ErrorMessage message={error} />
                        : <>
                            <h5 className='w100 alignCenter'>Post Comments({comments.length})</h5>
                            <div className='w100' id='comments-list'>
                                {comments.length ? <CommentsList comments={comments} setComments={setComments} setCommentsCount={setCommentsCount} /> : <NoCommentsFound />}
                            </div>
                            {commentable && <CreateCommentContainer setCommentsCount={setCommentsCount} postId={postId} setComments={setComments} />}
                        </>
            }
        </div>
    );
}

const PostCommentsLayout = ({ postId, commentable, closeShowCommentsList, setCommentsCount }) => {

    return (
        <div id='post-comments-layout' className='w100 h100 FRSC' onClick={closeShowCommentsList}>
            <CommentsContainer postId={postId} commentable={commentable} setCommentsCount={setCommentsCount} />
        </div>
    )
}

export default PostCommentsLayout