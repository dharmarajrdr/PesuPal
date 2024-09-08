import React from 'react'
import './Post.css'

const Post = ({ post }) => {
    const { title, author, content, created_at, likes, comments, media, mentions, tags } = post,
        { name, image } = author,
        convertDateAndTime = function (str) {
            try {
                const toTwoDigits = function (str) {
                    str = str + '';
                    return str.length == 2 ? str : '0' + str;
                }
                let d = new Date(str);
                if (d) {
                    let hours = d.getHours();
                    let am_pm = hours >= 12 ? 'PM' : 'AM';
                    hours = hours > 12 ? hours - 12 : hours;
                    return toTwoDigits(d.getDate()) + "/" + toTwoDigits(d.getMonth() + 1) + "/" + d.getFullYear() + " " + toTwoDigits(hours) + ":" + toTwoDigits(d.getMinutes()) + " " + am_pm;
                }
            } catch (error) {
                console.error({ 'module': convertDateAndTime, error, str });  //eslint-disable-line no-console
            }
        }, toggleMaxHeight = function (e) {
            const { target } = e;
            try {
                if (target.classList.contains('media_image')) {
                    const mediaContainer = target.parentNode;
                    mediaContainer.style.maxHeight = mediaContainer.style.maxHeight === '100%' ? null : '100%';
                }
            } catch (error) {
                console.error({ 'module': toggleMaxHeight, error });  //eslint-disable-line no-console
            }
        }
    return (
        <div className='Post w100'>
            <div className='PostHeader FRCB'>
                <div className='FRCS'>
                    <img src={image} alt={name} className='img_40_40 user_photo' />
                    <div className='FCSS'>
                        <h3 className='user_name'>{name}</h3>
                        <p className='created_at'>{convertDateAndTime(created_at)}</p>
                    </div>
                </div>
                <i className='fa-solid fa-ellipsis'></i>
            </div>
            <div className='PostBody FCSS'>
                {title ? <h4 className='postTitle'>{title}</h4> : null}
                <p className='postContent'>{content}</p>
                {media ?
                    <div className='mediaContainer FCSS w100' onClick={toggleMaxHeight}>
                        {media.map((media, index) => <img key={index} src={media} className='media_image w100' />)}
                    </div> : null}
            </div>
            <div className='PostFooter w100 FRCB'>
                <div className='FRCS'>
                    <div className='postActions leftFooter FRCC mY5'><i className="fa-regular fa-thumbs-up"></i> {likes}</div>
                    <div className='postActions leftFooter FRCC mY5'><i className="fa-regular fa-comment"></i> {comments}</div>
                </div>
                <div className='FRCE'>
                    <div className='postActions rightFooter FRCC mY5'><i className="fa-regular fa-bookmark"></i></div>
                </div>
                {/* <p>{mentions} Mentions</p>
                <p>{tags} Tags</p> */}
            </div>
        </div>
    )
}

export default Post