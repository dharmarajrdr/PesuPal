import Loader from "../../Loader";
import PostList from "./PostList";
import './ScheduledPostsLayout.css';
import { useDispatch } from "react-redux";
import { useEffect, useState } from "react";
import ErrorMessage from "../../ErrorMessage";
import { apiRequest } from "../../../http_request";
import { setActivePostId } from "../../../store/reducers/PostSlice";

const NoPostsAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5 w15'></i>
                No posts scheduled
            </p>
            <p className='w100 alignCenter'>Start scheduling posts to see them here.</p>
        </div>
    )
}

const ScheduledPosts = ({ posts }) => {

    return <>
        <PostList posts={posts} />
    </>
}

const ScheduledPostsLayout = () => {

    const size = 10; // Number of posts per page
    const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [posts, setPosts] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(false);


    useEffect(() => {

        setPage(-1);
        setPosts([]); // reset posts when 
        setLoading(true);

        apiRequest(`/api/v1/post/scheduled?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            setPosts(prevPosts => [...prevPosts, ...data]);
            setPage(prevPage => prevPage + 1);
            setHasMore(info.hasMoreRecords);
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
            setHasMore(false);
        });

    }, []);

    const overlayClickHandler = (e) => {
        if (e.target.id === 'scheduled-posts-layout') {
            dispatch(setActivePostId(null)); // Close the active post options when clicking outside
        }
    }

    return (
        <div id='scheduled-posts-layout' className='posts-layout FCCS w100 h100' onClick={overlayClickHandler}>
            <div id="postsList">
                {loading ? <Loader /> :
                    error ? <ErrorMessage /> :
                        posts.length ? <ScheduledPosts posts={posts} /> : <NoPostsAvailable />}
            </div>
            {hasMore && <Loader />}
        </div>
    )
}

export default ScheduledPostsLayout