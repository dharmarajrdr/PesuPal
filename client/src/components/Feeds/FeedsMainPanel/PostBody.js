import './PostBody.css';
import Poll from "./Poll"
import { NavLink } from "react-router-dom"
import PostMediaContainer from "./PostMediaContainer"

const PostDescription = ({ html }) => {

    html = html?.replace(/\t/mg, '<span style="padding-left: 2em;"></span>');   // `\t` -> indentation spaces
    html = html?.replace(/\n/mg, '<br/>');  // `\n` -> line breaks

    return (
        <div className="post-description html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />
    );
};

const TagsContainer = ({ tags }) => {
    return <div className='FRCS tagsContainer'>
        {tags && tags.map((tag, index) => (
            <NavLink to={`/feeds/tag/${tag.replace(/^#/m, '')}`} key={index} className='tagNavLink'>{tag}</NavLink>
        ))}
    </div>
}

const PostBody = ({ title, description, media, tags, poll, setPoll }) => {
    return <div className='PostBody FCSS'>
        {title ? <h4 className='postTitle'>{title}</h4> : null}
        <PostDescription html={description} />
        <TagsContainer tags={tags} />
        {poll && <Poll poll={poll} setPoll={setPoll} />}
        {media ? <PostMediaContainer media={media} key={media.id} /> : null}
    </div>
}
export default PostBody