import Loader from '../Loader';
import './SinglePostLayout.css';
import Post from './FeedsMainPanel/Post';
import { useParams } from 'react-router';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import { apiRequest } from '../../http_request';
import { showPopup } from '../../store/reducers/PopupSlice';

const SinglePostLayout = () => {

    const dispatch = useDispatch();
    const { postId } = useParams();
    const [post, setPost] = useState(null);
    const [loader, setLoader] = useState(true);

    useEffect(() => {
        apiRequest(`/api/v1/post/${postId}`, 'GET').then(({ data }) => {
            setPost(data);
            setLoader(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoader(false);
        });
    }, [postId])

    return <div id='single-posts-layout' className='posts-layout FCCS w100 h100'>
        {loader ? <Loader /> : (
            <div id='single-post-content'>
                <Post post={post} />
            </div>
        )}
    </div>
}

export default SinglePostLayout