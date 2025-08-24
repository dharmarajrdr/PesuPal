import Poll from "./Poll"
import { NavLink } from "react-router-dom"
import PostMediaContainer from "./PostMediaContainer"

const PostDescription = ({ html }) => <div className="post-description html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />

const TagsContainer = ({ tags }) => {
    return <div className='FRCS tagsContainer'>
        {tags && tags.map((tag, index) => (
            <NavLink to={`/feeds/tag/${tag.replace(/^#/m, '')}`} key={index} className='tagNavLink'>{tag}</NavLink>
        ))}
    </div>
}

const PostBody = ({ title, description, media, toggleMaxHeight, tags, poll, setPoll }) => {
    return <div className='PostBody FCSS'>
        {title ? <h4 className='postTitle'>{title}</h4> : null}
        <PostDescription html={description} />
        <TagsContainer tags={tags} />
        {poll && <Poll poll={poll} setPoll={setPoll} />}
        {media ? <PostMediaContainer media={media} toggleMaxHeight={toggleMaxHeight} key={media.id} /> : null}
    </div>
}
export default PostBody