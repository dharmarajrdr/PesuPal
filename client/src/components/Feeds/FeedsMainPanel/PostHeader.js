import utils from '../../../utils';
import PostOptions from './PostOptions';
import PostsLikedBy from './PostsLikedBy';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { setActivePostId } from '../../../store/reducers/PostSlice';

const PostHeader = ({ userId, displayName, displayPicture, createdAt, postId, commentable, setCommentable, isCreator, poll }) => {

    const dispatch = useDispatch();
    const [showLikesList, setShowLikesList] = useState(false);
    const { activePostId } = useSelector(state => state.posts); // only one can be open
    const [pollUpdatable, setPollUpdatable] = useState(poll?.updatable);

    const [isOptionOpen, setIsOptionOpen] = useState(false);

    const onToggleOption = () => {
        dispatch(setActivePostId(postId));
    }

    useEffect(() => {
        setIsOptionOpen(activePostId === postId);
    }, [activePostId, postId]);

    const isScheduledPost = new Date(createdAt) > Date.now();

    const createdAtInWords = isScheduledPost ? utils.futureTimeCalculator(createdAt) : utils.agoTimeCalculator(createdAt);

    return <div className='PostHeader FRCB'>
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_40_40 user_photo' onClick={() => { dispatch(showProfile(userId)); }} />
            <div className='FCSS'>
                <h3 className='user_name'>{displayName}</h3>
                <p className='created_at' title={utils.convertDateAndTime(createdAt)}>{createdAtInWords}</p>
            </div>
        </div>
        <i className='fa-solid fa-ellipsis cursP' onClick={onToggleOption}></i>
        {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} showLikesList={showLikesList} />}
        {isOptionOpen && <PostOptions pollUpdatable={pollUpdatable} setPollUpdatable={setPollUpdatable} postId={postId} commentable={commentable} setCommentable={setCommentable} isCreator={isCreator} poll={poll} setShowLikesList={setShowLikesList} />}
    </div>
}

export default PostHeader