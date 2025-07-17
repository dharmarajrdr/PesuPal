import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiRequest } from "../../../http_request";
import './UserPostsLayout.css'; // Assuming you have a CSS file for styling
import Loader from "../../Loader";
import ErrorMessage from "../../ErrorMessage";
import PostList from "../../Feeds/FeedsMainPanel/PostList";

const NoPostsAvailable = () => {

  return (
    <div className='FCCC w100 h100' id='no-data-found'>
      <p className='FRCC w100'>
        <i className='fa fa-exclamation-triangle mR5'></i>
        No posts available
      </p>
      <p className='w100 alignCenter'>User has not posted anything yet.</p>
    </div>
  )
}

const UserPostsLayout = () => {

  const size = 10; // Number of posts per page
  const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
  const { userId } = useParams();
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [hasMore, setHasMore] = useState(false);

  useEffect(() => {

    apiRequest(`/api/v1/post/user/${userId}?page=${page}&size=${size}&sort_order=${sortOrder}`, 'GET').then(({ data, info }) => {
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

  const [activePostId, setActivePostId] = useState(null); // only one can be open

  const overlayClickHandler = (e) => {
    if (e.target.id === 'user-posts-layout') {
      setActivePostId(null); // Close the active post options when clicking outside
    }
  }

  return (
    <div id='user-posts-layout' className='FCCS w100 h100' onClick={overlayClickHandler}>
      <div id="postsList">
        {loading ? <Loader /> :
          error ? <ErrorMessage /> :
            posts.length ? <PostList posts={posts} activePostId={activePostId} setActivePostId={setActivePostId} /> : <NoPostsAvailable />}
      </div>
      {hasMore && <Loader />}
    </div>
  )
}

export default UserPostsLayout