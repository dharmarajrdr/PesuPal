import React, { useState } from 'react'
import './FeedsLayout.css'
import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel'
import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel'

const FeedsLayout = () => {
    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState,
        width = {
            'leftNavOpened': "24.5%",
            "leftNavClosed": "85px"
        }
    return (
        <div id='FeedsLayout' className='Layout FRCS'>
            <FeedsLeftPanel leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <div className='FRSC h100' id='FeedsMain' width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} >
                <FeedsMainPanel />
                <FeedsRightPanel />
            </div>
        </div>
    )
}

export default FeedsLayout