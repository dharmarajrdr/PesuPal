import './PostCommentsLayout.css';
import Loader from '../../Loader';
import utils from '../../../utils';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import ErrorMessage from '../../ErrorMessage';
import { apiRequest } from '../../../http_request';
import EditCommentContainer from './EditCommentContainer';
import CreateCommentContainer from './CreateCommentContainer';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const NoCommentsFound = () => {
    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-comment-slash mR5 w20' /> No comments found
            </p>
            <p className='w100 alignCenter'>Create a comment to start the discussion</p>
        </div>
    )
}

const CommentHeader = ({ displayName, createdAt, isEditing }) => {

    return <div className='comment-user FRCB w100'>
        <h6 className='comment-user-name'>{displayName}</h6>
        {!isEditing && <span className='comment-date fs10 color777'>{utils.convertDateAndTime(createdAt)}</span>}
    </div>
}

const CommentContent = ({ html }) => <div className="comment-content html-content-renderer" dangerouslySetInnerHTML={{ __html: html }} />

const CommentFooter = ({ comment, post, showCommentActionIcon, showReplies, setShowReplies, clickedEditPostHandler, clickedDeletePostHandler }) => {

    const { creator: isCreator } = post || {};
    const { id, deletable, replyCount } = comment || {};

    const showEditIcon = deletable && showCommentActionIcon;
    const showDeleteIcon = (deletable || isCreator) && showCommentActionIcon;

    return <div className='comment-footer FRCB w100 mT10'>
        <div>
            <span className='fs12 cursP add-new-reply color555'><i className='fa fa-reply mR5 fs10 color777' />Reply</span>
            {replyCount > 0 && <>
                <span className='comment-reply-toggle-button fs12 cursP mL10 pL10' onClick={() => setShowReplies(!showReplies)}>
                    {/* {showReplies ? <i className='fa fa-chevron-up mR5' /> : <i className='fa fa-chevron-right mR5' />} */}
                    {showReplies ? 'Hide' : 'Show'} {replyCount} replies
                </span>
            </>}
        </div>
        <div className='FRCE'>
            {showEditIcon && <p className='fs12 cursP edit-comment color555 mL20' onClick={clickedEditPostHandler}><i className='fa fa-pencil w15 fs10 color777' />Edit</p>}
            {showDeleteIcon && <p className='fs12 cursP delete-comment color555 mL20' onClick={clickedDeletePostHandler}><i className='fa fa-trash w15 fs10 color777' />Delete</p>}
        </div>
    </div>
}

const Comment = ({ comment, setComments, setCommentsCount, post }) => {

    const dispatch = useDispatch();

    const { id, userId, displayName, displayPicture, message, createdAt } = comment || {};

    const [isEditing, setIsEditing] = useState(false);
    const [showReplies, setShowReplies] = useState(false);
    const [showCommentActionIcon, setShowCommentActionIcon] = useState(false);

    const deleteCommentHandler = () => {
        apiRequest(`/api/v1/post/comment/${id}`, 'DELETE').then(() => {
            setComments(prevComments => prevComments.filter(c => c.id !== id));
            setCommentsCount(prevCount => prevCount - 1);
            dispatch(showPopup({ message: 'Comment deleted successfully', type: 'success' }));
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const deletePopupOptions = [{
        "title": "Delete",
        "color": "#ff4d4d",
        "onClick": deleteCommentHandler
    }, {
        "title": "Cancel",
        "color": "gray",
    }];

    const clickedDeletePostHandler = () => {
        dispatch(showConfirmationPopup({ message: "Are you sure you want to delete this comment?", options: deletePopupOptions }));
    }

    const clickedEditPostHandler = () => {
        setIsEditing(true);
        setShowReplies(false);
        utils.autoFocusInput(`edit-comment-textarea-${id}`);
    }

    return (
        <div className='comment-item FRSS w100'>
            <div className='FCCS'>
                <img src={displayPicture} alt={displayName} className='comment-user-picture img_40_40 mR10' onClick={() => { dispatch(showProfile(userId)); }} />
            </div>
            <div className='FCSS comment-content-container'>
                <div className='comment-content-content w100' onMouseEnter={() => setShowCommentActionIcon(true)} onMouseLeave={() => setShowCommentActionIcon(false)}>
                    <CommentHeader displayName={displayName} createdAt={createdAt} isEditing={isEditing} />
                    {isEditing ? <EditCommentContainer id={id} message={message} setComments={setComments} setIsEditing={setIsEditing} setShowCommentActionIcon={setShowCommentActionIcon} /> :
                        <>
                            <CommentContent html={message} />
                            <CommentFooter post={post} comment={comment} showCommentActionIcon={showCommentActionIcon} showReplies={showReplies} setShowReplies={setShowReplies} clickedEditPostHandler={clickedEditPostHandler} clickedDeletePostHandler={clickedDeletePostHandler} />
                        </>
                    }
                </div>
                {showReplies && <CommentReply commentId={id} />}
            </div>
        </div>
    )
}

const CommentsList = ({ comments, post, setComments, setCommentsCount }) => {
    return <>
        {comments.map((comment, index) => <Comment key={index} comment={comment} setComments={setComments} setCommentsCount={setCommentsCount} post={post} />)}
    </>
}

const CommentReply = ({ commentId }) => {

    const dispatch = useDispatch();
    const [replies, setReplies] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        apiRequest(`/api/v1/post/comment/${commentId}/reply`, "GET").then(({ data }) => {
            setLoading(false);
            setReplies(data);
        }).catch(({ message }) => {
            setLoading(false);
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return loading ? <Loader /> : <div className='comment-replies-container mT10 bL1'>
        {replies.map((reply, index) => <Comment key={index} comment={reply} post={{}} setComments={() => { }} setCommentsCount={() => { }} />)}
    </div>;
}

const CommentsContainer = ({ post, setCommentsCount }) => {

    const { id: postId, commentable } = post || {};
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
                                {comments.length ? <CommentsList post={post} comments={comments} setComments={setComments} setCommentsCount={setCommentsCount} /> : <NoCommentsFound />}
                            </div>
                            {commentable && <CreateCommentContainer setCommentsCount={setCommentsCount} post={post} setComments={setComments} />}
                        </>
            }
        </div>
    );
}

const PostCommentsLayout = ({ post, closeShowCommentsList, setCommentsCount }) => {

    return (
        <div id='post-comments-layout' className='w100 h100 FRSC' onClick={closeShowCommentsList}>
            <CommentsContainer post={post} setCommentsCount={setCommentsCount} />
        </div>
    )
}

export default PostCommentsLayout