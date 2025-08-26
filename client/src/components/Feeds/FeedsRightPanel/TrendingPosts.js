import './TrendingPosts.css'
import Loader from '../../Loader.js';
import { Link } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../http_request.js';
import { showPopup } from '../../../store/reducers/PopupSlice.js';

const TrendingPosts = () => {

    const dispatch = useDispatch();
    const [posts, setPosts] = useState([]);
    const [loader, setLoader] = useState(true);

    useEffect(() => {
        setLoader(true);
        apiRequest(`/api/v1/post/trending?limit=3`, 'GET').then(({ data }) => {
            setPosts(data);
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
                </div> : posts.map(({ title, id, owner }, index) => {
                    const { displayName, displayPicture } = owner || {};
                    const route = `/posts/${id}`;
                    return (
                        <Link to={route} key={index} className='TrendingPost w100'>
                            <p className='title'>{title}</p>
                            <div className='FRCS'>
                                <img src={displayPicture} className='img_20_20 mR5' />
                                <span className='fs12 color777'>{displayName}</span>
                            </div>
                        </Link>
                    )
                })}
            </div>
        </div>
    )
}

export default TrendingPosts

