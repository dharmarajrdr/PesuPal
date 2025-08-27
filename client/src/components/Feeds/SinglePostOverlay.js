import './SinglePostOverlay.css';
import { useState } from 'react';
import Post from './FeedsMainPanel/Post';
import { useDispatch, useSelector } from 'react-redux';
import { hideSinglePost } from '../../store/reducers/SinglePostSlice';

const SinglePostOverlay = () => {

    const dispatch = useDispatch();
    const [, setShowPostLikesById] = useState(null);
    const { post } = useSelector(state => state.singlePost);

    const closeSinglePostOverlay = (e) => {
        if (e.target.id === 'single-post-overlay') {
            dispatch(hideSinglePost())
        }
    }

    return post && (
        <div id='single-post-overlay' className='entire-screen-overlay FRSC' onClick={closeSinglePostOverlay}>
            <div id='single-post-overlay-container'>
                <Post post={post} setShowPostLikesById={setShowPostLikesById} />
            </div>
        </div>
    )
}

export default SinglePostOverlay