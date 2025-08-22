import { useState } from 'react';
import './SettingsLayout.css';
import SettingsLeftContainer from './SettingsLeftContainer';
import SettingsMainContainer from './SettingsMainContainer';

const SettingsLayout = () => {

  const leftNavigationState = useState(true),
    [leftNavOpened,] = leftNavigationState,
    width = {
      'leftNavOpened': "20%",
      "leftNavClosed": "70px"
    };

  return (
    <div id='settings-layout' className='w100 h100 FRSS'>
      <SettingsLeftContainer leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
      <SettingsMainContainer width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} />
    </div>
  )
}

export default SettingsLayout