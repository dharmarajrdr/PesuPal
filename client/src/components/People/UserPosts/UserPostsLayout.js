import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiRequest } from "../../../http_request";
import Post from "../../Feeds/FeedsMainPanel/Post";
import './UserPostsLayout.css'; // Assuming you have a CSS file for styling
import Loader from "../../Loader";
import ErrorMessage from "../../ErrorMessage";

const UserPostsLayout = () => {

  const size = 10; // Number of posts per page
  const sortOrder = 'DESC'; // Sorting order for posts, can be 'ASC'
  const { userId } = useParams();
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [hasMore, setHasMore] = useState(true);

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

  return (
    <div id='user-posts-layout' className='FCCS w100 h100'>
      <div id="postsList">
        {error ? <ErrorMessage /> : posts.map((post, index) => (
          <Post post={post} key={index} />
        ))}
      </div>
      {hasMore && <Loader />}
    </div>
  )
}

export default UserPostsLayout