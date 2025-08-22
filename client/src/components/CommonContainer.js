import Profile from './OthersProfile/Profile'
import Popup from './Popup'
import ConfirmationPopup from './Utils/ConfirmationPopup'
import VerticalLoader from './VerticalLoader'

const CommonContainer = () => {

    return (
        <div>
            <Popup />
            <ConfirmationPopup />
            <VerticalLoader />
            <Profile />
        </div>
    )
}

export default CommonContainer