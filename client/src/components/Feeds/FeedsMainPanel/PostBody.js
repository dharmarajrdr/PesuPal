import './PostBody.css';
import Poll from "./Poll"
import { NavLink } from "react-router-dom"
import { useDispatch } from 'react-redux';
import PostMediaContainer from "./PostMediaContainer"
import { showProfile } from '../../../store/reducers/ProfileSlice';

const PostDescription = ({ html }) => {

    html = html?.replace(/\t/mg, '<span style="padding-left: 2em;"></span>');   // `\t` -> indentation spaces
    html = html?.replace(/\n/mg, '<br/>');  // `\n` -> line breaks
    html = html?.replace(/```(.*?)```/mg, (match, p1) => `<code><pre>${p1}</pre></code>`); // triple backticks -> code block

    return <div className="post-description html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />
};

const PostMentions = ({ mentions }) => {

    const dispatch = useDispatch();
    const { label, data } = mentions || {};
    const mentionedMoreThanTwo = data?.length > 2;
    const showMentions = data?.length > 0 && label;

    return showMentions && <div className='FRCS post-mentions'>
        <label>- {label}</label>
        {data.slice(0, 2).map((mention) => {
            const { id, displayName, displayPicture } = mention || {};
            return <div key={id} className='mentioned-member' onClick={() => dispatch(showProfile(id))}>
                <span className='display-name'>{displayName}</span>
            </div>
        })}
        {mentionedMoreThanTwo && <div className='more-mentioned-members'><span>and {data.length - 2} others</span></div>}
    </div>
}

const TagsContainer = ({ tags }) => {
    return <div className='FRCS tagsContainer'>
        {tags && tags.map((tag, index) => (
            <NavLink to={`/feeds/tag/${tag.replace(/^#/m, '')}`} key={index} className='tagNavLink'>{tag}</NavLink>
        ))}
    </div>
}

const PostBody = ({ title, description, media, mentions, tags, poll, setPoll }) => {
    return <div className='PostBody FCSS'>
        {title ? <h4 className='postTitle'>{title}</h4> : null}
        <PostDescription html={description} />
        <TagsContainer tags={tags} />
        {poll && <Poll poll={poll} setPoll={setPoll} />}
        {media ? <PostMediaContainer media={media} key={media.id} /> : null}
        {mentions && <PostMentions mentions={mentions} />}
    </div>
}
export default PostBody