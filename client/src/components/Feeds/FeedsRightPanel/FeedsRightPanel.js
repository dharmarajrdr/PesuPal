import './FeedsRightPanel.css'
import TrendingTags from './TrendingTags'
import TrendingPosts from './TrendingPosts'
import Quote from './Quote'
import CreateNewPost from '../FeedsMainPanel/CreateNewPost'
import { useState } from 'react'

const Header = () => {

    const [showCreatePostModal, setShowCreatePostModal] = useState(false);

    return <div className='FRCB w100 pR' id='FeedsRightPanelHeader'>
        {showCreatePostModal && <CreateNewPost onMinimize={() => setShowCreatePostModal(false)} />}
        <button id='create-post-button' onClick={() => setShowCreatePostModal(true)}>
            <i className="fa-solid fa-plus w15"></i>
            Create Post
        </button>
        <button className='my-posts-button'>
            <i className="fa-solid fa-book w15"></i>
            My Posts
        </button>
    </div>
}

const FeedsRightPanel = () => {
    return (
        <div id='FeedsRightPanel'>
            <Header />
            <div id='FeedsRightPanelContent' className='noScrollbar'>
                <TrendingTags />
                <TrendingPosts />
                <Quote quote="One day, you'll leave this world behind So live a life you will remember." author="Avicii" />
            </div>
        </div>
    )
}

export default FeedsRightPanel