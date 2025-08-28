import './FeedsLayout.css'
import Feeds from './Feeds'
import { useState } from 'react'
import TagPostsLayout from './TagPostsLayout'
import TagsListLayout from './TagsListLayout'
import PageNotFound from '../Auth/PageNotFound'
import { Route, Routes } from 'react-router-dom'
import SinglePostLayout from './SinglePostLayout'
import CreateNewPost from './FeedsMainPanel/CreateNewPost'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import BookmarkPostsLayout from './FeedsMainPanel/BookmarkPostsLayout'
import ScheduledPostsLayout from './FeedsMainPanel/ScheduledPostsLayout'

const FeedsLayout = () => {

    const leftNavigationState = useState(true);
    const [leftNavOpened,] = leftNavigationState;
    const width = {
        'leftNavOpened': "24.5%",
        "leftNavClosed": "90px"
    }

    return (
        <div id='FeedsLayout' className='Layout FRSS'>
            <CreateNewPost />
            <FeedsLeftPanel leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <Routes>
                <Route index element={<Feeds leftNavOpened={leftNavOpened} width={width} />} />
                <Route path='/bookmarks' element={<BookmarkPostsLayout />} />
                <Route path='/post/:postId' element={<SinglePostLayout />} />
                <Route path='/scheduled-posts' element={<ScheduledPostsLayout />} />
                <Route path='/tags' element={<TagsListLayout />} />
                <Route path='/tags/:tag' element={<TagPostsLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default FeedsLayout