import './AllPosts.css'
import PostList from './PostList'

const AllPosts = () => {

    const [activePostId, setActivePostId] = useState(null); // only one can be open

    return (
        <div className='FCSS AllPosts'>
            <PostList posts={[]} activePostId={activePostId} setActivePostId={setActivePostId} />
        </div>
    )
}

export default AllPosts