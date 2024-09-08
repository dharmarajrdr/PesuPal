import React from 'react'
import './FeedsMainPanel.css'
import PostYourThoughts from './PostYourThoughts'
import AllPosts from './AllPosts'

const FeedsMainPanel = () => {
  return (
    <div id='FeedsMainPanel'>
        <PostYourThoughts />
        <AllPosts />
    </div>
  )
}

export default FeedsMainPanel