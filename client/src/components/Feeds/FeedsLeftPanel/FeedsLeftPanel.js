import React from 'react'
import './FeedsLeftPanel.css'
import FeedsLeftPanelList from './FeedsLeftPanelList'
import FeedsLeftPanelItem from './FeedsLeftPanelItem'

const FeedsLeftPanel = () => {
    return (
        <div id='FeedsLeftPanel' className='h100'>
            {FeedsLeftPanelList.map((item, index) => <FeedsLeftPanelItem key={index} item={item} />)}
        </div>
    )
}

export default FeedsLeftPanel