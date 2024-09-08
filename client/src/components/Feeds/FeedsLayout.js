import React from 'react'
import './FeedsLayout.css'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import FeedList from './FeedList'
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel'

const FeedsLayout = () => {
    return (
        <div id='FeedsLayout' className='w100 h100 FRSS'>
            <FeedsLeftPanel />
            <div className='FRSC w100 h100' id='FeedsMain'>
                <FeedList />
                <FeedsRightPanel />
            </div>
        </div>
    )
}

export default FeedsLayout