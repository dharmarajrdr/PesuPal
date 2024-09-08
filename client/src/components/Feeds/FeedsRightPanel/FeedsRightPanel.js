import React from 'react'
import './FeedsRightPanel.css'
import TrendingTags from './TrendingTags'
import TrendingPosts from './TrendingPosts'
import Quote from './Quote'

const FeedsRightPanel = () => {
    return (
        <div id='FeedsRightPanel' className='noScrollbar'>
            <TrendingTags />
            <TrendingPosts />
            <Quote quote="One day, you'll leave this world behind So live a life you will remember." author="Avicii" />
        </div>
    )
}

export default FeedsRightPanel