import { useEffect, useState } from 'react'
import './PostsLikedBy.css'
import Loader from '../../Loader'
import ErrorMessage from '../../ErrorMessage'
import { apiRequest } from '../../../http_request'
import utils from '../../../utils'
import Profile from '../../OthersProfile/Profile'

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

const LikedByItem = ({ likedBy }) => {

    const [showProfile, setShowProfile] = useState(false);

    return <div key={likedBy.userId} className='likedByItem FRCB w100'>
        {showProfile && <Profile userId={likedBy.userId} setShowProfile={setShowProfile} />}
        <div className='FRCS'>
            <img src={likedBy.displayPicture} alt={likedBy.displayName} className='img_30_30 mR10' onClick={() => setShowProfile(true)} />
            <h6>{likedBy.displayName}</h6>
        </div>
        <span className='fs10 color777'>{utils.convertDateAndTime(likedBy.createdAt)}</span>
    </div>
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
                                {likedBy.map((like, index) => <LikedByItem likedBy={like} key={index} />)}
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