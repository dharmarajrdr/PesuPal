import { useEffect, useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import UserAvatar from '../../User/UserAvatar';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';

const CreateCommentContainer = ({ post, setComments, setCommentsCount }) => {

    const dispatch = useDispatch();
    const [comment, setComment] = useState('');
    const [isAnonymous, setIsAnonymous] = useState(false);
    const myProfile = useSelector(state => state.myProfile);
    const [displayPicture, setDisplayPicture] = useState(null);
    const [showEmojiPicker, setShowEmojiPicker] = useState(false);
    const { id: postId, allowAnonymousComments, creator: isCreator } = post || {};

    useEffect(() => {
        const epr_c90x4z = document.querySelector('.epr_c90x4z');
        if (epr_c90x4z) {
            epr_c90x4z.style.display = 'none';
        }
    }, [showEmojiPicker]);

    useEffect(() => {
        setDisplayPicture(isAnonymous ? '/images/anonymous.jpg' : myProfile?.displayPicture);
    }, [isAnonymous]);

    const submitCommentHandler = () => {
        if (!comment.trim()) { return; }
        setShowEmojiPicker(false);
        apiRequest(`/api/v1/post/comment`, "POST", { 'message': comment, postId, 'anonymous': isAnonymous }).then(({ data }) => {
            setComment('');
            setComments(prevComments => [data, ...prevComments]);
            setCommentsCount(prevCount => prevCount + 1);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    const toggleAnonymousHandler = (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (isCreator) {
            return dispatch(showPopup({ message: `You cannot comment anonymously on your own post.`, type: 'error' }));
        }
        if (allowAnonymousComments) {
            setIsAnonymous(prev => !prev);
        }
    }

    const onEmojiClick = (emojiData) => {
        setComment((prevComment) => prevComment + emojiData.emoji);
    };

    return (
        <div id='create-comment' className='FRCC w100'>
            <div onClick={toggleAnonymousHandler} id='comment-anonymous-toggle' title={isAnonymous ? 'Comment as Anonymous' : null} className={`comment-anonymous-toggle FCCS cursP mR10 ${isAnonymous ? 'anonymous' : ''} ${!allowAnonymousComments ? 'disabled' : ''}`}>
                <UserAvatar displayName={null} displayPicture={displayPicture} />
            </div>
            <div className='w100 pR FRSC'>
                <textarea className='create-comment-textarea w100' autoFocus={true} placeholder='Write a comment...' value={comment} onChange={(e) => setComment(e.target.value)} />
                <i className='fa fa-smile cursP' id='create-comment-emoji-toggle' title='Insert Emoji' onClick={() => setShowEmojiPicker(prev => !prev)} />
                {showEmojiPicker && <div id='create-comment-emoji-picker'>
                    <EmojiPicker onEmojiClick={onEmojiClick} height={300} width={350} lazyLoadEmojis={true} />
                </div>}
            </div>
            <button className='create-comment-button' onClick={submitCommentHandler}>
                <i className='fa fa-paper-plane mR5' /> Post
            </button>
        </div>
    );
};

export default CreateCommentContainer;