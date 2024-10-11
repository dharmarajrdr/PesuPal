import React from 'react'
import ListOfTeamNavigations from './ListOfTeamNavigations';
import './TeamLeftContainer.css'
import TeamLeftContainerItem from './TeamLeftContainerItem';

const TeamLeftContainer = () => {
    return (
        <div id='TeamLeftContainer' className='FCSS p10 custom-scrollbar'>
            {ListOfTeamNavigations.map((item, index) => <TeamLeftContainerItem key={index} item={item} />)}
        </div>
    )
}

export default TeamLeftContainer