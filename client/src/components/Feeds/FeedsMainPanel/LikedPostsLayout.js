import Loader from "../../Loader";
import PostList from "./PostList";
import './LikedPostsLayout.css';
import { useEffect, useState } from "react";
import ErrorMessage from "../../ErrorMessage";
import { apiRequest } from "../../../http_request";
import { useDispatch, useSelector } from "react-redux";
import { clearPosts, setActivePostId, setPosts } from "../../../store/reducers/PostSlice";

const NoPostsAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5 w15'></i>
                No posts liked
            </p>
            <p className='w100 alignCenter'>Start liking posts to see them here.</p>
        </div>
    )
}

const LikedPostsLayout = () => {

    const size = 10; // Number of posts per page
    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(false);
    const { list: posts } = useSelector(state => state.posts);

    useEffect(() => {

        setPage(-1);
        dispatch(clearPosts([])); // reset posts when 
        setLoading(true);

        apiRequest(`/api/v1/post/like/all?page=${page}&size=${size}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            dispatch(setPosts(data));
            setHasMore(info.hasMoreRecords);
            setPage(prevPage => prevPage + 1);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
            setHasMore(false);
        });

    }, []);

    const overlayClickHandler = (e) => {
        if (e.target.id === 'liked-posts-layout') {
            dispatch(setActivePostId(null)); // Close the active post options when clicking outside
        }
    }

    return (
        <div id='liked-posts-layout' className='posts-layout FCCS w100 h100' onClick={overlayClickHandler}>
            <div id="postsList">
                {loading ? <Loader /> :
                    error ? <ErrorMessage /> :
                        posts.length ? <PostList /> : <NoPostsAvailable />}
            </div>
            {hasMore && <Loader />}
        </div>
    )
}

export default LikedPostsLayout;