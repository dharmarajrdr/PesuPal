import { useState } from "react";
import './EditCommentContainer.css';
import { useDispatch } from "react-redux";
import { apiRequest } from "../../../http_request";
import { showPopup } from "../../../store/reducers/PopupSlice";

const EditCommentContainer = ({ id, message, setComments, setIsEditing, setShowCommentActionIcon }) => {

    const dispatch = useDispatch();
    const [comment, setComment] = useState(message);

    const saveEditedCommentHandler = () => {

        if (comment == message) {
            return setIsEditing(false);
        }

        if (!comment.trim()) {
            return dispatch(showPopup({ message: 'Comment cannot be empty', type: 'error' }));
        }

        apiRequest(`/api/v1/post/comment/${id}`, 'PATCH', { 'message': comment }).then(({ message }) => {
            setComments(prevComments => prevComments.map(c => c.id === id ? { ...c, 'message': comment } : c));
            dispatch(showPopup({ message, type: 'success' }));
            setIsEditing(false);
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }

    return <div className='edit-comment-container w100'>
        <div>
            <textarea className={`edit-comment-textarea w100 mT5`} id={`edit-comment-textarea-${id}`} placeholder='Edit your comment...' value={comment} onChange={(e) => setComment(e.target.value)}></textarea>
        </div>
        <div className='FRCS mT5'>
            <button className={`save-button mR5`} onClick={saveEditedCommentHandler}>Save</button>
            <button onClick={() => { setIsEditing(false); setShowCommentActionIcon(false); }}>Cancel</button>
        </div>
    </div>
}

export default EditCommentContainer;