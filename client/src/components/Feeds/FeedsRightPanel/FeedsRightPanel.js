import Quote from './Quote'
import './FeedsRightPanel.css'
import TrendingTags from './TrendingTags';
import { useDispatch } from 'react-redux';
import TrendingPosts from './TrendingPosts';
import { resetPostData, showCreatePostModal } from '../../../store/reducers/PostSlice';

const FeedsRightPanel = () => {
    return (
        <div id='FeedsRightPanel'>
            <div id='FeedsRightPanelContent' className='noScrollbar'>
                <TrendingTags />
                <TrendingPosts />
                <Quote />
            </div>
        </div>
    )
}

export default FeedsRightPanel