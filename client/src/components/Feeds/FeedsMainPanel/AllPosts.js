import React from 'react'
import './AllPosts.css'
import PostsList from './PostsList.js'
import Post from './Post'

const AllPosts = () => {
    return (
        <div className='FCSS'>
            {PostsList.map((post, index) => <Post key={index} post={post} />)}
        </div>
    )
}

export default AllPosts