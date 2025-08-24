import './PostMediaContainer.css';
import { useDispatch } from "react-redux";
import { showFullScreenImageAt } from "../../../store/reducers/FullScreenImageSlice";

const PostMediaContainer = ({ media }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (index) => {
        dispatch(showFullScreenImageAt({
            mediaUrls: media,
            currentIndex: index
        }));
    }

    return <div className='mediaContainer FCSS w100'>
        {media.map((mediaItem, index) => <img key={index} src={mediaItem} className='media_image w100' onClick={() => showFullScreenImageHandler(index)} />)}
    </div>
}

export default PostMediaContainer