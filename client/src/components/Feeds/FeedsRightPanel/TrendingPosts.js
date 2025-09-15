import './TrendingPosts.css'
import Loader from '../../Loader.js';
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../http_request.js';
import { useDispatch, useSelector } from "react-redux";
import { showPopup } from '../../../store/reducers/PopupSlice.js';
import { showSinglePost } from '../../../store/reducers/SinglePostSlice.js';
import { setTrendingPosts } from '../../../store/reducers/TrendingPostsSlice.js';

const TrendingPosts = () => {

    const dispatch = useDispatch();
    const { posts } = useSelector(state => state.trendingPosts);
    const [loader, setLoader] = useState(true);

    useEffect(() => {
        setLoader(true);
        apiRequest(`/api/v1/post/trending?limit=3`, 'GET').then(({ data }) => {
            dispatch(setTrendingPosts(data));
            setLoader(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoader(false);
        });
    }, []);

    return (
        <div id='TrendingPosts' className='FCSS selectNone'>
            <p id='title' className='w100'>
                <i className='fa-regular fa-newspaper w20 mR5' style={{ color: 'orange' }} ></i>Trending posts
            </p>
            <div className='FRCC w100' id='TrendingPostsList'>
                {loader ? <div className='FRCC w100'>
                    <Loader />
                </div> : posts.length ? posts.map((post, index) => {
                    const { title, description, owner } = post || {};
                    const { displayName, displayPicture } = owner || {};
                    const showSinglePostHandler = () => {
                        dispatch(showSinglePost(post));
                    }
                    return (
                        <div key={index} className='TrendingPost w100' onClick={showSinglePostHandler}>
                            {title ? <p className='title'>{title}</p> : <div className='description' dangerouslySetInnerHTML={{ __html: description }} />}
                            <div className='FRCS'>
                                <img src={displayPicture} className='img_20_20 mR5' />
                                <span className='fs12 color777'>{displayName}</span>
                            </div>
                        </div>
                    )
                }) : <p className='FCCC w100 color777 fs12 p20'>No posts found!</p>}
            </div>
        </div>
    )
}

export default TrendingPosts

