import { useState } from 'react';
import './PostOptions.css';
import PostsLikedBy from './PostsLikedBy';
import { apiRequest } from '../../../http_request';
import ConfirmationPopup from '../../Utils/ConfirmationPopup';

const PostOptions = ({ postId, commentable, setCommentable, isCreator }) => {

    const [showLikesList, setShowLikesList] = useState(false);
    const [deletePostClicked, setDeletePostClicked] = useState(false);

    const toggleCommentSectionHandler = () => {
        apiRequest(`/api/v1/post/${postId}`, "PATCH", { commentable: !commentable }).then(({ data }) => {
            setCommentable(!commentable);
        }).catch(({ message }) => {
            console.error({ 'module': toggleCommentSectionHandler, message });  //eslint-disable-line no-console
        });
    }

    const deletePopupOptions = [
        {
            title: "Yes",
            color: "green",
            onClick: () => {
                apiRequest(`/api/v1/post/${postId}`, "DELETE").then(() => {
                    setDeletePostClicked(false);
                    window.location.reload();
                }).catch(({ message }) => {
                    console.error({ 'module': 'deletePopupOptions', message }); // eslint-disable-line no-console
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
        <div className="FCSS" id="post-options">

            {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} />}
            <div className='option' onClick={() => setShowLikesList(true)}>Show Post Likes</div>

            {isCreator && <>
                <div className='option' onClick={toggleCommentSectionHandler} >{commentable ? 'Disable' : 'Enable'} Post Comments</div>
                <div className='option' onClick={() => setDeletePostClicked(true)}>Delete Post</div>
                {deletePostClicked && <ConfirmationPopup message={"Are you sure you want to delete this post?"} onClose={() => setDeletePostClicked(false)} options={deletePopupOptions} />}
            </>}
        </div>
    )
}

export default PostOptions