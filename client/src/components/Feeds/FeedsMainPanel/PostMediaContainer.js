import { useDispatch } from "react-redux";
import { showFullScreenImage } from "../../../store/reducers/FullScreenImageSlice";

const PostMediaContainer = ({ media, toggleMaxHeight }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (mediaItem) => {
        dispatch(showFullScreenImage(mediaItem));
    }

    return <div className='mediaContainer FCSS w100' onClick={toggleMaxHeight}>
        {media.map((mediaItem, index) => <img key={index} src={mediaItem} className='media_image w100' onClick={() => showFullScreenImageHandler(mediaItem)} />)}
    </div>
}

export default PostMediaContainer