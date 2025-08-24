import Post from './Post';
import { useDispatch, useSelector } from 'react-redux';
import { setActivePostId } from '../../../store/reducers/PostSlice';

const PostList = ({ posts }) => {

    const dispatch = useDispatch();
    const { activePostId } = useSelector(state => state.posts); // only one can be open

    const onToggleOption = (post) => {
        dispatch(setActivePostId(post.id));
    }

    return <>
        {posts.map((post, index) => <Post isOptionOpen={activePostId === post.id} onToggleOption={() => onToggleOption(post)} key={index} post={post} />)}
    </>
}

export default PostList