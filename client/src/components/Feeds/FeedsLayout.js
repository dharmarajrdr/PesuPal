import './FeedsLayout.css'
import Feeds from './Feeds'
import { useState } from 'react'
import TagPostsLayout from './TagPostsLayout'
import PageNotFound from '../Auth/PageNotFound'
import { Route, Routes } from 'react-router-dom'
import CreateNewPost from './FeedsMainPanel/CreateNewPost'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import ScheduledPostsLayout from './FeedsMainPanel/ScheduledPostsLayout'

const FeedsLayout = () => {

    const leftNavigationState = useState(true);
    const [leftNavOpened,] = leftNavigationState;
    const [showCreatePostModal, setShowCreatePostModal] = useState(false);
    const width = {
        'leftNavOpened': "24.5%",
        "leftNavClosed": "90px"
    }

    return (
        <div id='FeedsLayout' className='Layout FRCS'>
            {showCreatePostModal && <CreateNewPost onMinimize={() => setShowCreatePostModal(false)} />}
            <FeedsLeftPanel leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <Routes>
                <Route path='/' element={<Feeds setShowCreatePostModal={setShowCreatePostModal} leftNavOpened={leftNavOpened} width={width} />} />
                <Route path='/scheduled-posts' element={<ScheduledPostsLayout />} />
                <Route path='/tag/:tag' element={<TagPostsLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default FeedsLayout