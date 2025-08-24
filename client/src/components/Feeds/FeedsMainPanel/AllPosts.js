import './AllPosts.css'
import PostList from './PostList';
import Loader from '../../Loader';
import { useEffect, useState } from 'react'
import ErrorMessage from '../../ErrorMessage';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { appendPosts, clearPosts } from '../../../store/reducers/PostSlice';

const NoPostsAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5 w15'></i>
                No posts available
            </p>
            <p className='w100 alignCenter'>Start creating posts to see them here.</p>
        </div>
    )
}

const AllPosts = () => {

    const size = 10; // Number of posts per page
    const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(false);
    const { list: posts } = useSelector(state => state.posts);

    // TODO: Temporarily fetching scheduled posts, change to fetch all posts later
    useEffect(() => {
        if (page == 0) {
            dispatch(clearPosts());
        }
        apiRequest(`/api/v1/post/scheduled?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            dispatch(appendPosts(data));
            setHasMore(info.hasMoreRecords);
            setPage(prevPage => prevPage + 1);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoading(false);
            setError(message);
            setHasMore(false);
        });
    }, [dispatch, page]);

    return (
        <div className='FCCS AllPosts'>
            <div id="postsList">
                {loading ? <Loader /> :
                    error ? <ErrorMessage /> :
                        posts.length ? <PostList /> : <NoPostsAvailable />}
            </div>
            {hasMore && <Loader />}
        </div>
    )
}

export default AllPosts