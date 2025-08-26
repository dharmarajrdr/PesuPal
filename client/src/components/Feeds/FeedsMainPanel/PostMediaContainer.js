import { useState } from 'react';
import './PostMediaContainer.css';
import { useDispatch } from "react-redux";
import { showFullScreenImageAt } from "../../../store/reducers/FullScreenImageSlice";

const ResourceNotFound = ({ style }) => <div className='resource-not-found FRCC' style={{ ...style }}>
    <i className='fa fa-exclamation-triangle w20 mR5'></i>
    <span>Resource Not Found</span>
</div>

const ImageItem = ({ url, index, onClick, style }) => {
    const [resourceNotFound, setResourceNotFound] = useState(false);
    return resourceNotFound ? <ResourceNotFound style={{ ...style }} /> : <img src={url} className='media_image' style={{ ...style }} onClick={() => onClick(index)} onError={() => setResourceNotFound(true)} />
}

const OneImage = ({ media, onClick }) => {

    const { url } = media;

    return <div className='one_image_container FRCC w100'>
        <ImageItem url={url} index={0} onClick={onClick} />
    </div>
}

const TwoImages = ({ media, onClick }) => {

    return <div className='two_images_container FRCC w100'>
        {media.map(({ url }, index) => <ImageItem key={index} url={url} index={index} onClick={onClick} style={{ width: '50%' }} />)}
    </div>
}

const MoreThanTwoImages = ({ media, onClick }) => {

    return <div className='more_than_two_images_container FRCS w100'>
        <div style={{ width: '75%' }} >
            <ImageItem url={media[0].url} index={0} onClick={onClick} style={{ width: '100%' }} />
        </div>
        <div style={{ width: '25%' }} className='FCSS'>
            {media.slice(1, 3).map(({ url }, index) => {
                const showOverlay = index == 1 && media.length > 3;
                return <div className='pR FRCC small-image-container'>
                    {showOverlay ? <div className='FRCC more-imagesoverlay'>
                        <span>+{media.length - 3}</span>
                    </div> : null}
                    <ImageItem key={index} url={url} index={index + 1} onClick={onClick} style={{ width: '100%' }} />
                </div>
            })}
        </div>
    </div >
}

const PostMediaContainer = ({ media }) => {

    const dispatch = useDispatch();

    const showFullScreenImageHandler = (index) => {

        const mediaUrls = media.map(({ url }) => url);
        dispatch(showFullScreenImageAt({ mediaUrls, 'currentIndex': index }));
    }

    const componentToRender = () => {
        if (media.length === 1) {
            return <OneImage media={media[0]} onClick={() => showFullScreenImageHandler(0)} />
        } else if (media.length === 2) {
            return <TwoImages media={media} onClick={showFullScreenImageHandler} />
        } else if (media.length > 2) {
            return <MoreThanTwoImages media={media} onClick={showFullScreenImageHandler} />
        } else {
            return null;
        }
    }

    return media.length > 0 ? <div className='mediaContainer FCSS w100'>
        {componentToRender()}
    </div> : null;
}

export default PostMediaContainer