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
        <div className='pR' id='feeds-notifications'>
            <i className="fa-solid fa-bell"></i>
            <b className='notifyCount'>17</b>
        </div>
        <button id='create-post-button' onClick={showCreatePostModalHandler}>
            <i className="fa-solid fa-plus w15"></i>Create Post
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