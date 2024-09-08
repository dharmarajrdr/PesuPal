import React from 'react'
import './FeedsRightPanel.css'
import TrendingTags from './TrendingTags'

const FeedsRightPanel = () => {
  return (
    <div id='FeedsRightPanel' className='noScrollbar'>
        <TrendingTags />
    </div>
  )
}

export default FeedsRightPanel