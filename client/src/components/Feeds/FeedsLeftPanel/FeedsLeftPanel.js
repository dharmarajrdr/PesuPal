import React from 'react'
import './FeedsLeftPanel.css'
import FeedsLeftPanelList from './FeedsLeftPanelList'
import FeedsLeftPanelItem from './FeedsLeftPanelItem'
import FeedsLeftPanelPages from './FeedsLeftPanelPages'

const FeedsLeftPanel = () => {
    return (
        <div id='FeedsLeftPanel' className='h100'>
            {FeedsLeftPanelList.map((item, index) => <FeedsLeftPanelItem key={index} item={item} />)}
            <FeedsLeftPanelPages />
        </div>
    )
}

export default FeedsLeftPanel