import React from 'react'
import ListOfTeamNavigations from './ListOfTeamNavigations';
import './TeamLeftContainer.css'
import TeamLeftContainerItem from './TeamLeftContainerItem';

const TeamLeftContainer = ({ leftNavigationState, width }) => {
    const [leftNavOpened, setLeftNavOpened] = leftNavigationState,
        openCloseLeftNav = () => setLeftNavOpened(!leftNavOpened);
    return (
        <div id='TeamLeftContainer' className='FCSS p10 custom-scrollbar' style={{width}}>
            <div id='openCloseLeftNavigationContainer' className='w100 FRCE'>
                <i class={"fa-solid fa-angles-" + (leftNavOpened ? 'left' : 'right')} onClick={openCloseLeftNav}></i>
            </div>
            {ListOfTeamNavigations.map((item, index) => <TeamLeftContainerItem key={index} item={item} leftNavOpened={leftNavOpened} />)}
        </div>
    )
}

export default TeamLeftContainer