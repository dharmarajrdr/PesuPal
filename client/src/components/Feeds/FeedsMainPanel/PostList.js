import Post from './Post';

const PostList = ({ posts, activePostId, setActivePostId }) => {

    return <>
        {posts.map((post, index) =>
            <Post isOptionOpen={activePostId === post.id}
                onToggleOption={() => {
                    setActivePostId((prev) => (prev === post.id ? null : post.id));
                }} key={index} post={post} />)}
    </>
}

export default PostList