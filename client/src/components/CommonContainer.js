import Popup from './Popup'
import Profile from './OthersProfile/Profile'
import VerticalLoader from './VerticalLoader'
import FullScreenImageView from './FullScreenImageView'
import ConfirmationPopup from './Utils/ConfirmationPopup'

const CommonContainer = () => {

    return (
        <div>
            <Popup />
            <ConfirmationPopup />
            <VerticalLoader />
            <Profile />
            <FullScreenImageView />
        </div>
    )
}

export default CommonContainer