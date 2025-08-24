import './Post.css'
import Poll from './Poll';
import { useState } from 'react';
import PostHeader from './PostHeader';
import PostFooter from './PostFooter';
import { useDispatch } from 'react-redux';
import { NavLink } from 'react-router-dom';
import { showFullScreenImage } from '../../../store/reducers/FullScreenImageSlice';

const PostDescription = ({ html }) => <div className="post-description html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />

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