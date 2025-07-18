import { useState } from 'react';
import './PostOptions.css';
import { apiRequest } from '../../../http_request';
import ConfirmationPopup from '../../Utils/ConfirmationPopup';

const PostOptions = ({ postId, commentable, setCommentable, isCreator, poll, pollUpdatable, setPollUpdatable, setPopupData, setShowLikesList }) => {

    const [deletePostClicked, setDeletePostClicked] = useState(false);

    const cleanupPopupAfterTimeout = () => {
        setTimeout(() => {
            setPopupData(null);
        }, 3000);
    }

    const closeOptionsModal = () => {
        document.getElementById('tag-posts-layout').click();
    }

    const toggleCommentSectionHandler = () => {
        apiRequest(`/api/v1/post/${postId}`, "PATCH", { commentable: !commentable }).then(({ data }) => {
            setCommentable(!commentable);
            setPopupData({ message: `Post comments ${!commentable ? 'enabled' : 'disabled'}`, type: 'success' });
            closeOptionsModal();
            cleanupPopupAfterTimeout();
        }).catch(({ message }) => {
            closeOptionsModal();
            console.error({ message });  //eslint-disable-line no-console
        });
    }

    const pollUpdateHandler = () => {
        apiRequest(`/api/v1/post/poll/${poll.id}`, "PATCH", { "votesUpdatable": !pollUpdatable }).then(({ data }) => {
            setPollUpdatable(!pollUpdatable);
            setPopupData({ message: `Poll update ${!pollUpdatable ? 'enabled' : 'disabled'}`, type: 'success' });
            closeOptionsModal();
            cleanupPopupAfterTimeout();
        }).catch(({ message }) => {
            closeOptionsModal();
            console.error({ message });  //eslint-disable-line no-console
        });
    }

    const deletePopupOptions = [
        {
            title: "Yes",
            color: "green",
            onClick: () => {
                apiRequest(`/api/v1/post/${postId}`, "DELETE").then(() => {
                    setDeletePostClicked(false);
                    closeOptionsModal();
                    window.location.reload();
                }).catch(({ message }) => {
                    closeOptionsModal();
                    console.error({ message }); // eslint-disable-line no-console
                });
            }
        },
        {
            title: "No",
            color: "red",
            onClick: () => setDeletePostClicked(false)
        }
    ];

    return (
        <div div className="FCSS" id="post-options" >

            <div className='option' onClick={() => { setShowLikesList(true); closeOptionsModal(); }}>Show Post Likes</div>

            {
                isCreator && <>

                    {poll && <div className='option' onClick={pollUpdateHandler} >{pollUpdatable ? 'Disable' : 'Enable'} Poll Update</div>}

                    <div className='option' onClick={toggleCommentSectionHandler} >{commentable ? 'Disable' : 'Enable'} Post Comments</div>

                    <div className='option' onClick={() => setDeletePostClicked(true)}>Delete Post</div>
                    {deletePostClicked && <ConfirmationPopup message={"Are you sure you want to delete this post?"} onClose={() => setDeletePostClicked(false)} options={deletePopupOptions} />}

                </>
            }
        </div>
    )
}

export default PostOptions