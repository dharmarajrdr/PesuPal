import './AllPosts.css'
import PostList from './PostList';
import Loader from '../../Loader';
import ErrorMessage from '../../ErrorMessage';
import { apiRequest } from '../../../http_request';
import { useEffect, useRef, useState } from 'react';
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
    const [fetching, setFetching] = useState(false);
    const { list: posts } = useSelector(state => state.posts);

    const loadMorePostsRef = useRef(null);

    useEffect(() => {
        if (page == 0) {
            dispatch(clearPosts());
        }
        apiRequest(`/api/v1/post/feeds?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            dispatch(appendPosts(data));
            setHasMore(info.hasMoreRecords);
            setFetching(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
            setLoading(false);
            setError(message);
            setHasMore(false);
            setFetching(false);
        });
    }, [page]);

    useEffect(() => {

        if (!hasMore || fetching) return;

        const observer = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting && !loading) {
                setPage(prev => prev + 1);
                setFetching(true);
            }
        }, { threshold: 1.0 });

        const target = loadMorePostsRef.current;
        if (target) observer.observe(target);

        return () => {
            if (target) observer.unobserve(target);
        };
    }, [hasMore, fetching, loading]);

    return (
        <div className='FCCS' id='AllPosts'>
            <div id="postsList">
                {loading ? <Loader /> :
                    error ? <ErrorMessage /> :
                        posts.length ? <PostList /> : <NoPostsAvailable />}
            </div>
            {hasMore && <div id='load-more-posts' ref={loadMorePostsRef} className='FCCC mT20'>
                <Loader />
            </div>}
        </div>
    )
}

export default AllPosts