import './Post.css'
import { useState } from 'react';
import PostBody from './PostBody';
import PostHeader from './PostHeader';
import PostFooter from './PostFooter';

const Post = ({ post }) => {

    const { id, title, owner, description, createdAt, media, mentions, bookmarked, tags, bookmarkable, creator: isCreator } = post,
        { userId, displayName, displayPicture } = owner;

    const [poll, setPoll] = useState(post.poll);
    const [commentable, setCommentable] = useState(post.commentable);

    return (
        <div className='Post w100'>
            <PostHeader userId={userId} postId={id} displayName={displayName} displayPicture={displayPicture} createdAt={createdAt} commentable={commentable} setCommentable={setCommentable} isCreator={isCreator} poll={poll} />
            <PostBody mentions={mentions} title={title} description={description} media={media} tags={tags} poll={poll} setPoll={setPoll} />
            <PostFooter post={post} commentable={commentable} bookmarkable={bookmarkable} bookmarked={bookmarked} />
        </div>
    )
}

export default Post