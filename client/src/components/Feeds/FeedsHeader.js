import './FeedsHeader.css'
import NotifyCount from '../NotifyCount';
import { useDispatch, useSelector } from 'react-redux';
import { resetPostData, showCreatePostModal } from '../../store/reducers/PostSlice';

const FeedsSearchBar = ({ searchText, setSearchText }) => {

    return <div className='FRCE w100 pR' id='FeedsSearchBar'>
        <input type='text' placeholder='Search...' spellCheck='false' autoComplete='off' value={searchText} onChange={(e) => setSearchText(e.target.value)} />
        <i className='fa fa-search'></i>
    </div>
}

const HeaderRight = () => {

    const dispatch = useDispatch();
    const { hasPrivilegeToCreatePost } = useSelector(state => state.posts);

    const showCreatePostModalHandler = () => {

        dispatch(resetPostData());
        dispatch(showCreatePostModal());
    }

    return <div className='FRCS pR' id='FeedsRightPanelHeader'>
        <div className='pR' id='feeds-notifications'>
            <i className="fa-solid fa-bell w15"></i>
            <NotifyCount count={0} />
        </div>
        {hasPrivilegeToCreatePost && <button id='create-post-button' onClick={showCreatePostModalHandler} className='FRCC'>
            <i className="fa-solid fa-plus w15"></i>
            <span className='colorFFF'>Create Post</span>
        </button>}
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