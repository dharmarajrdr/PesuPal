import './FeedsHeader.css'
import { useDispatch } from 'react-redux';
import { resetPostData, showCreatePostModal } from '../../store/reducers/PostSlice';

const FeedsSearchBar = ({ searchText, setSearchText }) => {

    return <div className='FRCE w100 pR' id='FeedsSearchBar'>
        <input type='text' placeholder='Search...' spellCheck='false' autoComplete='off' value={searchText} onChange={(e) => setSearchText(e.target.value)} />
        <i className='fa fa-search'></i>
    </div>
}

const HeaderRight = () => {

    const dispatch = useDispatch();

    const showCreatePostModalHandler = () => {

        dispatch(resetPostData());
        dispatch(showCreatePostModal());
    }

    return <div className='FRCS pR' id='FeedsRightPanelHeader'>
        <button id='create-post-button' onClick={showCreatePostModalHandler}>
            <i className="fa-solid fa-plus w15"></i>
            Create Post
        </button>
        <button className='my-posts-button'>
            <i className="fa-solid fa-book w15"></i>
            My Posts
        </button>
    </div>
}

const FeedsHeader = ({ searchText, setSearchText }) => {
    return (
        <div className='FRCS w100' id='FeedsHeader'>
            <FeedsSearchBar searchText={searchText} setSearchText={setSearchText} />
            <HeaderRight />
        </div>
    )
}

export default FeedsHeader