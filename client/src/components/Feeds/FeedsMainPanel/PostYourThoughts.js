import React from 'react'
import './PostYourThoughts.css'

const PostYourThoughts = () => {
    return (
        <div id='PostYourThoughts' className='FCSS'>
            <label>Post Something</label>
            <div className='FRSB w100' id='img_text_file'>
                <img src='/images/Users/user_10.jpg' className='img_40_40' />
                <textarea placeholder="What's on your mind?" spellCheck="false" autoComplete='off'></textarea>
            </div>
            <div className='w100 FRCB'>
                <div className='FRCS'>
                    <span className='actions_post_creation'>
                        <i className='fa-regular fa-image'></i>
                        <span>Add attachment</span>
                    </span>
                    <span className='actions_post_creation'>
                        <i className='fa-regular fa-hashtag'></i>
                        <span>Add tag</span>
                    </span>
                    {/* <span className='actions_post_creation'>
                        <i className='fa-regular fa-at'></i>
                        Add mentions
                    </span> */}
                    <span className='actions_post_creation'>
                        <i className='fa-solid fa-t'></i>
                        <span>Add title</span>
                    </span>
                    <span className='actions_post_creation'>
                        <i className="fa-solid fa-square-poll-vertical"></i>
                        <span>Add poll</span>
                    </span>
                </div>
                <div>
                    <span className='actions_post_creation'>
                        <i className='fa-regular fa-calendar-days'></i>
                        <span>Schedule post</span>
                    </span>
                    <button id='shareMyThoughtButton'>Share</button>
                </div>
            </div>
        </div>
    )
}

export default PostYourThoughts