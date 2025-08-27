import './PostMessagePermissionDenied.css';

const PostMessagePermissionDenied = () => {
    return (
        <div id='post-message-permission-denied' className='w100 FRCC p20'>
            <i className="fa-solid fa-comment-slash mR5"></i>
            <p>
                <span>This group is only for announcements.</span>
                <span className='mL5'>You do not have permission to post messages here.</span>
            </p>
        </div>
    )
}

export default PostMessagePermissionDenied