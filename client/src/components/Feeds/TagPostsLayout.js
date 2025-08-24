import './TagPostsLayout.css'; // Assuming you have a CSS file for styling
import Loader from "../Loader";
import ErrorMessage from "../ErrorMessage";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiRequest } from "../../http_request";
import PostList from "./FeedsMainPanel/PostList";
import { useDispatch, useSelector } from "react-redux";
import { setActivePostId, setPosts } from "../../store/reducers/PostSlice";

const NoPostsAvailable = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-exclamation-triangle mR5'></i>
                No posts available
            </p>
            <p className='w100 alignCenter'>There are no posts available for this tag.</p>
        </div>
    )
}

const TagsPageHeader = ({ tag }) => {

    return (
        <div id='tags-page-header' className="FRCC w100 p10">
            <h1 className='tags-page-title'>#{tag}</h1>
        </div>
    )
}

const TagsPage = ({ tag }) => {

    return <>
        <TagsPageHeader tag={tag} />
        <PostList />
    </>
}

const TagPostsLayout = () => {

    const size = 10; // Number of posts per page
    const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
    const { tag } = useParams();
    const dispatch = useDispatch();
    const [page, setPage] = useState(0);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [hasMore, setHasMore] = useState(false);
    const { list: posts } = useSelector(state => state.posts);

    useEffect(() => {

        setLoading(true);
        setPosts([]); // reset posts when tag changes
        setPage(-1);

        apiRequest(`/api/v1/post/tag/${tag}?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
            setLoading(false);
            setHasMore(info.hasMoreRecords);
            setPage(prevPage => prevPage + 1);
            dispatch(setPosts(data));
        }).catch(({ message }) => {
            setLoading(false);
            setError(message);
            setHasMore(false);
            dispatch(setPosts([]));
        });

    }, [tag]);

    const overlayClickHandler = (e) => {
        if (e.target.id === 'tag-posts-layout') {
            dispatch(setActivePostId(null)); // Close the active post options when clicking outside
        }
    }

    return (
        <div id='tag-posts-layout' className='posts-layout FCCS w100 h100' onClick={overlayClickHandler}>
            <div id="postsList">
                {loading ? <Loader /> :
                    error ? <ErrorMessage /> :
                        posts.length ? <TagsPage tag={tag} /> : <NoPostsAvailable />}
            </div>
            {hasMore && <Loader />}
        </div>
    )
}

export default TagPostsLayout