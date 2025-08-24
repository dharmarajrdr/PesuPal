import Post from './Post';

const PostList = ({ posts }) => {

    return <>
        {posts.map((post, index) => <Post key={index} post={post} />)}
    </>
}

export default PostList