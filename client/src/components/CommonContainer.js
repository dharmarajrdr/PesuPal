import Popup from './Popup'
import ConfirmationPopup from './Utils/ConfirmationPopup'
import VerticalLoader from './VerticalLoader'

const CommonContainer = () => {

    return (
        <div>
            <Popup />
            <ConfirmationPopup />
            <VerticalLoader />
        </div>
    )
}

export default CommonContainer