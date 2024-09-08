import React from 'react'
import './TrendingPosts.css'
import { Link } from 'react-router-dom'
import TrendingPostsList from './TrendingPostsList.js'

const TrendingPosts = () => {
    return (
        <div id='TrendingPosts' className='FCSS selectNone'>
            <p id='title' className='w100'>
                <i className='fa-regular fa-newspaper mR5' style={{ color: 'orange' }} ></i>Trending posts
            </p>
            <div className='FRCC' id='TrendingPostsList'>
                {TrendingPostsList.map(({ title, route, author }, index) => {
                    return (
                        <Link to={route} key={index} className='TrendingPost w100'>
                            <p className='title'>{title}</p>
                            <div className='FRCS'>
                                <img src={author.image} className='img_20_20 mR5' />
                                <span className='fs14 color777'>{author.name}</span>
                            </div>
                        </Link>
                    )
                })}
            </div>
        </div>
    )
}

export default TrendingPosts

