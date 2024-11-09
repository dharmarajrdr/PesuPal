import React, { useState } from 'react'

import TeamLeftContainer from './TeamLeftContainer'
import TeamMainContainer from './TeamMainContainer'

const TeamLayout = () => {
    const leftNavigationState = useState(true),
        [leftNavOpened,] = leftNavigationState;
    return (
        <div id='TeamLayout' className='Layout FRSS'>
            <TeamLeftContainer leftNavigationState={leftNavigationState} width={leftNavOpened ? "20%" : "75px"} />
            <TeamMainContainer width={leftNavOpened ? "calc(100% - 20%)" : "calc(100% - 75px)"} />
        </div>
    )
}

export default TeamLayout