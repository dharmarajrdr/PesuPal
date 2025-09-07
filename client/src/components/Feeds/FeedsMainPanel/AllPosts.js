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
        <div className='FCCC w100' style={{ height: 'calc(100vh - 100px)' }} id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5 w15'></i>
                No posts available
            </p>
            <p className='w100 alignCenter'>Start creating posts to see them here.</p>
        </div>
    )
}

const AllPosts = ({ searchText }) => {

    const size = 10; // Number of posts per page
    const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(false);
    const [fetching, setFetching] = useState(false);
    const { list: posts } = useSelector(state => state.posts);

    const firstRender = useRef(true);
    const loadMorePostsRef = useRef(null);

    const fetchPosts = async () => {
        apiRequest(`/api/v1/post/feeds?search=${searchText}&page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
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
    }

    useEffect(() => {
        if (firstRender.current) {
            firstRender.current = false;
            return;
        }

        const text = searchText.trim();

        // Case 1: search cleared → show default feed
        if (text.length === 0) {
            setPage(0);
            setLoading(true);
            setError(null);
            setHasMore(false);
            setFetching(false);
            dispatch(clearPosts());
            fetchPosts({ search: null }); // fetch default feed
            return;
        }

        // Case 2: less than 3 chars → skip API
        if (text.length < 3) {
            return;
        }

        // Case 3: 3+ chars → debounce search
        const delayDebounceFn = setTimeout(() => {
            setPage(0);
            setLoading(true);
            setError(null);
            setHasMore(false);
            setFetching(false);
            dispatch(clearPosts());
            fetchPosts({ search: text }); // fetch with query
        }, 700); // 700ms debounce is standard

        return () => clearTimeout(delayDebounceFn);
    }, [searchText]);


    useEffect(() => {
        if (page == 0) {
            dispatch(clearPosts());
        }
        fetchPosts();
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
            <div id="postsList" className='w100'>
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