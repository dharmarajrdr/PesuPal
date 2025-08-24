import './AllPosts.css'
import PostList from './PostList'
import { useEffect, useState } from 'react'
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from 'react-redux';
import { setPosts } from '../../../store/reducers/PostSlice';
import { showPopup } from '../../../store/reducers/PopupSlice';

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
        apiRequest(`/api/v1/post/scheduled?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            dispatch(setPosts(data));
            setHasMore(info.hasMoreRecords);
            setPage(prevPage => prevPage + 1);
        }).catch(({ message }) => {
            dispatch(showPopup(message, { type: 'error' }));
            setLoading(false);
            setError(message);
            setHasMore(false);
        });
    }, []);

    return (
        <div className='FCSS AllPosts'>
            <PostList />
        </div>
    )
}

export default AllPosts