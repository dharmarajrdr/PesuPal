import { useState } from 'react'
import './FeedsLayout.css'
import Popup from '../Popup'
import { UsePopupFromSession } from '../../UsePopupFromSession'
import Feeds from './Feeds'
import { Route, Routes } from 'react-router-dom'
import PageNotFound from '../Auth/PageNotFound'
import TagPostsLayout from './TagPostsLayout'

const FeedsLayout = () => {

    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState,
        width = {
            'leftNavOpened': "24.5%",
            "leftNavClosed": "85px"
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