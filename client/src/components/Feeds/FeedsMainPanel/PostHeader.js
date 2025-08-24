import utils from '../../../utils';
import PostsLikedBy from './PostsLikedBy';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../../http_request';
import OptionsModal from '../../Utils/OptionsModal';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { showProfile } from '../../../store/reducers/ProfileSlice';
import { deletePost, setActivePostId } from '../../../store/reducers/PostSlice';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';

const PostHeader = ({ userId, displayName, displayPicture, createdAt, postId, commentable, setCommentable, isCreator, poll }) => {

    const dispatch = useDispatch();
    const [isOptionOpen, setIsOptionOpen] = useState(false);
    const [showLikesList, setShowLikesList] = useState(false);
    const { activePostId } = useSelector(state => state.posts); // only one can be open
    const [pollUpdatable, setPollUpdatable] = useState(poll?.updatable);

    const onToggleOption = () => {
        dispatch(setActivePostId(postId));
    }

    useEffect(() => {
        setIsOptionOpen(activePostId === postId);
    }, [activePostId, postId]);

    const isScheduledPost = new Date(createdAt) > Date.now();

    const createdAtInWords = isScheduledPost ? utils.futureTimeCalculator(createdAt) : utils.agoTimeCalculator(createdAt);

    const closeOptionsModal = () => {
        setIsOptionOpen(false);
        dispatch(setActivePostId(null));
    }

    const options = [
        {
            name: isCreator && isScheduledPost && `Post Now`,
            icon: `fa fa-paper-plane`,
            onClick: (e) => {
                e.stopPropagation();
                closeOptionsModal();

            }
        },
        {
            name: isCreator && !isScheduledPost && `View Likes`,
            icon: `fa fa-users`,
            onClick: (e) => {
                e.stopPropagation();
                setShowLikesList(true);
                closeOptionsModal();
            }
        },
        {
            name: !isScheduledPost && 'Copy Link',
            icon: 'fa fa-link',
            onClick: (e) => {
                e.stopPropagation();
                navigator.clipboard.writeText(`${window.location.origin}/post/${postId}`);
                closeOptionsModal();
                dispatch(showPopup({ message: 'Post link copied to clipboard', type: 'success' }));
            }
        },
        {
            name: isCreator && poll && `${pollUpdatable ? 'Disable' : 'Enable'} Poll Update`,
            icon: 'fa fa-poll',
            onClick: (e) => {
                e.stopPropagation();
                dispatch(showConfirmationPopup({
                    message: `Are you sure you want to ${pollUpdatable ? 'disable' : 'enable'} poll updates?`,
                    options: [
                        {
                            title: `${pollUpdatable ? 'Disable' : 'Enable'}`,
                            color: `${pollUpdatable ? 'red' : 'green'}`,
                            onClick: () => {
                                apiRequest(`/api/v1/post/poll/${poll.id}`, "PATCH", { "votesUpdatable": !pollUpdatable }).then(({ data }) => {
                                    setPollUpdatable(!pollUpdatable);
                                    dispatch(showPopup({ message: `Poll update ${!pollUpdatable ? 'enabled' : 'disabled'}`, type: 'success' }));
                                    closeOptionsModal();
                                }).catch(({ message }) => {
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: "Cancel",
                            color: "gray",
                            onClick: () => dispatch(hideConfirmationPopup())
                        }
                    ]
                }));
            }
        },
        {
            name: isCreator && `${commentable ? 'Disable' : 'Enable'} Comments`,
            icon: 'fa fa-comments',
            onClick: (e) => {
                e.stopPropagation();
                dispatch(showConfirmationPopup({
                    message: `Are you sure you want to ${commentable ? 'disable' : 'enable'} comments?`,
                    options: [
                        {
                            title: `${commentable ? 'Disable' : 'Enable'}`,
                            color: `${commentable ? 'red' : 'green'}`,
                            onClick: () => {
                                apiRequest(`/api/v1/post/${postId}`, "PATCH", { 'commentable': !commentable }).then(({ data }) => {
                                    setCommentable(!commentable);
                                    dispatch(showPopup({ message: `Post comments ${!commentable ? 'enabled' : 'disabled'}`, type: 'success' }));
                                    closeOptionsModal();
                                }).catch(({ message }) => {
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: "Cancel",
                            color: "gray",
                            onClick: () => dispatch(hideConfirmationPopup())
                        }
                    ]
                }));
            }
        },
        {
            name: isCreator && 'Delete Post',
            icon: 'fa fa-trash',
            onClick: (e) => {
                e.stopPropagation();
                dispatch(showConfirmationPopup({
                    message: 'Are you sure you want to delete this post? This action cannot be undone.',
                    options: [
                        {
                            title: "Delete",
                            color: "red",
                            onClick: () => {
                                apiRequest(`/api/v1/post/${postId}`, "DELETE").then(({ message }) => {
                                    dispatch(hideConfirmationPopup());
                                    dispatch(showPopup({ message, type: 'success' }));
                                    dispatch(deletePost(postId));
                                }).catch(({ message }) => {
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: "Cancel",
                            color: "gray",
                            onClick: () => dispatch(hideConfirmationPopup())
                        }
                    ]
                }));
            }
        }
    ];

    return <div className='PostHeader FRCB pR'>
        <div className='FRCS'>
            <img src={displayPicture} alt={displayName} className='img_40_40 user_photo' onClick={() => { dispatch(showProfile(userId)); }} />
            <div className='FCSS'>
                <h3 className='user_name'>{displayName}</h3>
                <p className='created_at' title={utils.convertDateAndTime(createdAt)}>{createdAtInWords}</p>
            </div>
        </div>
        <i className='fa-solid fa-ellipsis cursP' onClick={onToggleOption}></i>
        {showLikesList && <PostsLikedBy postId={postId} closeShowLikesList={() => setShowLikesList(false)} showLikesList={showLikesList} />}
        {isOptionOpen && <OptionsModal options={options} style={{ top: '35px', right: '-20px' }} />}
    </div>
}

export default PostHeader