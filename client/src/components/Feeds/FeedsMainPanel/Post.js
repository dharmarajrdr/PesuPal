import './Post.css'
import { useState } from 'react';
import PostBody from './PostBody';
import PostHeader from './PostHeader';
import PostFooter from './PostFooter';
import { useDispatch } from 'react-redux';
import { showFullScreenImage } from '../../../store/reducers/FullScreenImageSlice';

const Post = ({ post }) => {

    const dispatch = useDispatch();

    const { id, title, owner, description, createdAt, media, bookmarked, tags, bookmarkable, creator: isCreator } = post,
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

    const [poll, setPoll] = useState(post.poll);
    const [commentable, setCommentable] = useState(post.commentable);

    return (
        <div className='Post w100'>
            <PostHeader userId={userId} postId={id} displayName={displayName} displayPicture={displayPicture} createdAt={createdAt} commentable={commentable} setCommentable={setCommentable} isCreator={isCreator} poll={poll} />
            <PostBody title={title} description={description} media={media} toggleMaxHeight={toggleMaxHeight} tags={tags} poll={poll} setPoll={setPoll} />
            <PostFooter post={post} commentable={commentable} bookmarkable={bookmarkable} bookmarked={bookmarked} />
        </div>
    )
}

export default Post