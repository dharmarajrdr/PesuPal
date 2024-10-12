import React from 'react'

import TeamLeftContainer from './TeamLeftContainer'
import TeamMainContainer from './TeamMainContainer'

const TeamLayout = () => {
    return (
        <div id='TeamLayout' className='Layout FRSS'>
            <TeamLeftContainer />
            <TeamMainContainer />
        </div>
    )
}

export default TeamLayout