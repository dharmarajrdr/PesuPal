import './PostTagContainer.css';
import { useDispatch } from "react-redux";
import { showPopup } from "../../../store/reducers/PopupSlice";

const PostTagContainer = ({ tags, setTags }) => {

    const dispatch = useDispatch();

    const addTagHandler = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            const newTag = `#${e.target.value.trim()}`;
            if (newTag.match(/^#[\w-]+$/) === null) {
                return dispatch(showPopup({ message: "Invalid tag format! Tags can only contain letters, numbers, underscores, and hyphens.", type: 'error' }));
            }
            if (newTag && !tags.includes(newTag)) {
                setTags([...tags, newTag]);
                e.target.value = '';
            }
        }
    }

    const removeTagHandler = (e) => {
        const tagToRemove = e.target.previousSibling.textContent;
        setTags(tags.filter(tag => tag !== tagToRemove));
    }

    return <div className='FRCS' id='create-post-tags'>
        {tags.map((tag, index) => (
            <div className='create-post-tag FRCC' key={index}>
                <span>{tag}</span>
                <i className="fa-solid fa-xmark" onClick={removeTagHandler}></i>
            </div>
        ))}
        <input type='text' placeholder='Add Tag' autoComplete='off' id='create-tag-input' onKeyDown={addTagHandler} />
    </div>
}

export default PostTagContainer