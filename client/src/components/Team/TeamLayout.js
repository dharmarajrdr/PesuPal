import React, { useState } from 'react'

import TeamLeftContainer from './TeamLeftContainer'
import TeamMainContainer from './TeamMainContainer'

const TeamLayout = () => {
    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState,
        width = {
            'leftNavOpened': "20%",
            "leftNavClosed": "70px"
        }
    return (
        <div id='TeamLayout' className='Layout FRSS'>
            <TeamLeftContainer leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <TeamMainContainer width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} />
        </div>
    )
}

export default TeamLayout