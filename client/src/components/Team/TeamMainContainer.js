import React from 'react'
import './TeamMainContainer.css';
import ManageWorkLayout from './ManageWork/ManageWorkLayout';
import DriveLayout from './Drive/DriveLayout';

const TeamMainContainer = () => {
  return (
    <div id='TeamMainContainer'>
        {/* <DriveLayout /> */}
        <ManageWorkLayout />
    </div>
  )
}

export default TeamMainContainer