import './PostMediaContainer.css';
import { useDispatch } from "react-redux";
import { showFullScreenImageAt } from "../../../store/reducers/FullScreenImageSlice";

const OneImage = ({ mediaUrl, onClick }) => {

    return <div className='one_image_container FRCC w100'>
        <img src={mediaUrl} className='media_image' onClick={onClick} />
    </div>
}

const TwoImages = ({ mediaUrls, onClick }) => {

    return <div className='two_images_container FRCC w100'>
        <img src={mediaUrls[0]} className='media_image' style={{ width: '50%' }} onClick={() => onClick(0)} />
        <img src={mediaUrls[1]} className='media_image' style={{ width: '50%' }} onClick={() => onClick(1)} />
    </div>
}

const MoreThanTwoImages = ({ mediaUrls, onClick }) => {

    return <div className='more_than_two_images_container FRCS w100'>
        <div style={{ width: '75%' }} >
            <img src={mediaUrls[0]} className='media_image w100' onClick={() => onClick(0)} />
        </div>
        <div style={{ width: '25%' }} className='FCSS'>
            {mediaUrls.slice(1, 3).map((url, index) => {
                const showOverlay = index == 1 && mediaUrls.length > 3;
                return <div className='pR FRCC small-image-container'>
                    {showOverlay ? <div className='FRCC more-imagesoverlay'>
                        <span>+{mediaUrls.length - 3}</span>
                    </div> : null}
                    <img key={index} src={url} className={`media_image w100 ${showOverlay ? 'partially-visible' : ''}`} onClick={() => onClick(index + 1)} />
                </div>
            })}
        </div>
    </div >
}

const PostMediaContainer = ({ media }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (index) => {
        dispatch(showFullScreenImageAt({
            mediaUrls: media,
            currentIndex: index
        }));
    }

    const componentToRender = () => {
        if (media.length === 1) {
            return <OneImage mediaUrl={media[0]} onClick={() => showFullScreenImageHandler(0)} />
        } else if (media.length === 2) {
            return <TwoImages mediaUrls={media} onClick={showFullScreenImageHandler} />
        } else if (media.length > 2) {
            return <MoreThanTwoImages mediaUrls={media} onClick={showFullScreenImageHandler} />
        } else {
            return null;
        }
    }

    return <div className='mediaContainer FCSS w100'>
        {componentToRender()}
    </div>
}

export default PostMediaContainer