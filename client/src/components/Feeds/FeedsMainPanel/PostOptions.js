import './PostOptions.css';
import { useDispatch } from 'react-redux';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { deletePost } from '../../../store/reducers/PostSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const PostOptions = ({ postId, commentable, setCommentable, isCreator, poll, pollUpdatable, setPollUpdatable, setShowLikesList }) => {

    const dispatch = useDispatch();

    const closeOptionsModal = () => {
        const postsLayout = document.getElementsByClassName('posts-layout');
        if (postsLayout) {
            postsLayout[0].click();
        }
    }

    const showPostLikesHandler = (e) => {
        e.stopPropagation();
        setShowLikesList(true);
        closeOptionsModal();
    }

    const toggleCommentSectionHandler = (e) => {
        e.stopPropagation();
        apiRequest(`/api/v1/post/${postId}`, "PATCH", { 'commentable': !commentable }).then(({ data }) => {
            setCommentable(!commentable);
            dispatch(showPopup({ message: `Post comments ${!commentable ? 'enabled' : 'disabled'}`, type: 'success' }));
            closeOptionsModal();
        }).catch(({ message }) => {
            closeOptionsModal();
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const pollUpdateHandler = (e) => {
        e.stopPropagation();
        apiRequest(`/api/v1/post/poll/${poll.id}`, "PATCH", { "votesUpdatable": !pollUpdatable }).then(({ data }) => {
            setPollUpdatable(!pollUpdatable);
            dispatch(showPopup({ message: `Poll update ${!pollUpdatable ? 'enabled' : 'disabled'}`, type: 'success' }));
            closeOptionsModal();
        }).catch(({ message }) => {
            closeOptionsModal();
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const copyPostHandler = (e) => {
        e.stopPropagation();
        navigator.clipboard.writeText(`${window.location.origin}/post/${postId}`);
        closeOptionsModal();
        dispatch(showPopup({ message: 'Post link copied to clipboard', type: 'success' }));
    }

    const deletePostHandler = (e) => {
        e.stopPropagation();
        dispatch(showConfirmationPopup({
            message: 'Are you sure you want to delete this post? This action cannot be undone.',
            options: [
                {
                    title: "Delete",
                    color: "red",
                    onClick: () => {
                        apiRequest(`/api/v1/post/${postId}`, "DELETE").then(({ message }) => {
                            dispatch(hideConfirmationPopup());
                            dispatch(showPopup({ message, type: 'success' }));
                            dispatch(deletePost(postId));
                            closeOptionsModal();
                        }).catch(({ message }) => {
                            closeOptionsModal();
                            dispatch(showPopup({ message, type: 'error' }));
                        });
                    }
                },
                {
                    title: "Cancel",
                    color: "gray",
                    onClick: () => dispatch(hideConfirmationPopup())
                }
            ]
        }));
    }

    return (
        <div className="FCSS" id="post-options" >

            <div className='option' onClick={showPostLikesHandler}>Show Post Likes</div>

            <div className='option' onClick={copyPostHandler}>Copy Link</div>

            {
                isCreator && <>

                    {poll && <div className='option' onClick={pollUpdateHandler} >{pollUpdatable ? 'Disable' : 'Enable'} Poll Update</div>}

                    <div className='option' onClick={toggleCommentSectionHandler} >{commentable ? 'Disable' : 'Enable'} Post Comments</div>

                    <div className='option' onClick={deletePostHandler}>Delete Post</div>

                </>
            }
        </div>
    )
}

export default PostOptions