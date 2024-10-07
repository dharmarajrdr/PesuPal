import React from 'react'
import './FeedsLayout.css'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel'
import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel'

const FeedsLayout = () => {
    return (
        <div id='FeedsLayout' className='Layout FRCS'>
            <FeedsLeftPanel />
            <div className='FRSC w100 h100' id='FeedsMain'>
                <FeedsMainPanel />
                <FeedsRightPanel />
            </div>
        </div>
    )
}

export default FeedsLayout