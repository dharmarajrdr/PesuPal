import { useState } from 'react';
import './PostOptions.css';
import PostsLikedBy from './PostsLikedBy';
import { apiRequest } from '../../../http_request';

const PostOptions = ({ postId, commentable, setCommentable, isCreator }) => {

    const [showLikesList, setShowLikesList] = useState(false);

    const toggleCommentSectionHandler = () => {
        apiRequest(`/api/v1/post/${postId}`, "PATCH", { commentable: !commentable }).then(({ data }) => {
            setCommentable(!commentable);
        }).catch(({ message }) => {
            console.error({ 'module': toggleCommentSectionHandler, message });  //eslint-disable-line no-console
        });
    }

    return (
        <div className="FCSS" id="post-options">

            {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} />}
            <div className='option' onClick={() => setShowLikesList(true)}>Show Post Likes</div>

            {isCreator && <div className='option' onClick={toggleCommentSectionHandler} >{commentable ? 'Disable' : 'Enable'} Post Comments</div>}
        </div>
    )
}

export default PostOptions