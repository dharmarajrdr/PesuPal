import { useEffect, useState } from 'react'
import './PostsLikedBy.css'
import Loader from '../../Loader'
import ErrorMessage from '../../ErrorMessage'
import { apiRequest } from '../../../http_request'
import utils from '../../../utils'
import Profile from '../../OthersProfile/Profile'
import UserPreview from '../../User/UserPreview'

const NoLikesFound = () => {

    return (
        <div className='FCCC w100 h100P' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5'></i>
                No likes found
            </p>
            <p className='w100 alignCenter'>There are no users who liked this post.</p>
        </div>
    )
}

const PostsLikedByContainer = ({ postId }) => {

    const [likedBy, setLikedBy] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        apiRequest(`/api/v1/post/like/${postId}`, 'GET').then(({ data }) => {
            setLoading(false);
            setLikedBy(data);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
        });
    }, []);

    return (
        <div id='PostsLikedByContainer' className='FCCS mT20'>
            {
                loading ? <Loader />
                    : error ? <ErrorMessage message={error} />
                        : <>
                            <h5 className='w100 alignCenter'>Post Likes({likedBy.length})</h5>
                            {likedBy.length ? <>
                                {likedBy.map((user_detail, index) => <UserPreview user_detail={user_detail} key={index} />)}
                            </> : <NoLikesFound />}
                        </>
            }
        </div>
    );
}

const PostsLikedBy = ({ postId, closeShowLikesList }) => {

    return (
        <div id='PostsLikedByOverlay' className='w100 h100 FRSC' onClick={closeShowLikesList}>
            <PostsLikedByContainer postId={postId} />
        </div>
    )
}

export default PostsLikedBy