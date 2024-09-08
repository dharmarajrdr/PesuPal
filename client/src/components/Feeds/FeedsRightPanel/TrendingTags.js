import React from 'react'
import './TrendingTags.css'
import { Link } from 'react-router-dom'
import TrendingTagsList from './TrendingTagsList'

const TrendingTags = () => {
    return (
        <div id='TrendingTags' className='FCSS'>
            <p id='title'>Trending Tags</p>
            <div className='FRCC' id='TrendingTagsList'>
                {TrendingTagsList.map(({ title, route }, index) => {
                    return (
                        <Link to={route} key={index} className='TrendingTag'>{title}</Link>
                    )
                })}
            </div>
        </div>
    )
}

export default TrendingTags