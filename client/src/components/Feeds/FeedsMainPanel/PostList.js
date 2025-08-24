import Post from './Post';
import { useSelector } from 'react-redux';

const PostList = () => {

    const { list: posts } = useSelector(state => state.posts);

    return <>
        {posts.map((post, index) => <Post key={index} post={post} />)}
    </>
}

export default PostList