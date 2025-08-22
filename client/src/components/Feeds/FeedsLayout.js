import './FeedsLayout.css'
import Feeds from './Feeds'
import { useState } from 'react'
import TagPostsLayout from './TagPostsLayout'
import PageNotFound from '../Auth/PageNotFound'
import { Route, Routes } from 'react-router-dom'

const FeedsLayout = () => {

    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState,
        width = {
            'leftNavOpened': "24.5%",
            "leftNavClosed": "90px"
        }
    return (
        <div id='FeedsLayout' className='Layout FRCS'>
            <Routes>
                <Route path='/' element={<Feeds leftNavigationState={leftNavigationState} leftNavOpened={leftNavOpened} width={width} />} />
                <Route path='/tag/:tag' element={<TagPostsLayout />} />
                <Route path="*" element={<PageNotFound />} />
            </Routes>
        </div>
    )
}

export default FeedsLayout