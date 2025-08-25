import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel';
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel';

const Feeds = ({ leftNavOpened, width, setShowCreatePostModal }) => {

    return <div className='FRSC h100' id='FeedsMain' width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} >
        <FeedsMainPanel />
        <FeedsRightPanel setShowCreatePostModal={setShowCreatePostModal} />
    </div>
}

export default Feeds