import React from 'react'
import './FeedsMainPanel.css'
import CreateNewPost from './CreateNewPost'
import AllPosts from './AllPosts'

const FeedsMainPanel = () => {
  return (
    <div id='FeedsMainPanel'>
      <CreateNewPost />
      <AllPosts />
    </div>
  )
}

export default FeedsMainPanel