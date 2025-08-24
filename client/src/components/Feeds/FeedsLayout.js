import './FeedsLayout.css'
import Feeds from './Feeds'
import { useState } from 'react'
import TagPostsLayout from './TagPostsLayout'
import PageNotFound from '../Auth/PageNotFound'
import { Route, Routes } from 'react-router-dom'
import PermissionDenied from '../Auth/PermissionDenied'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'

const FeedsLayout = () => {

    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState,
        width = {
            'leftNavOpened': "24.5%",
            "leftNavClosed": "90px"
        }
    return (
        <div id='FeedsLayout' className='Layout FRCS'>
            <FeedsLeftPanel leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <Routes>
                <Route path='/' element={<Feeds leftNavOpened={leftNavOpened} width={width} />} />
                <Route path='/scheduled-posts' element={<PermissionDenied />} />
                <Route path='/tag/:tag' element={<TagPostsLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default FeedsLayout