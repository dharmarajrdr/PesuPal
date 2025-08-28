import Popup from './Popup'
import Profile from './OthersProfile/Profile'
import VerticalLoader from './VerticalLoader'
import FullScreenImageView from './FullScreenImageView'
import ConfirmationPopup from './Utils/ConfirmationPopup'
import SinglePostOverlay from './Feeds/SinglePostOverlay'

const CommonContainer = () => {

    return (
        <div>
            <Popup />
            <ConfirmationPopup />
            <VerticalLoader />
            <Profile />
            <FullScreenImageView />
            <SinglePostOverlay />
        </div>
    )
}

export default CommonContainer