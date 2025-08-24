import { useState } from 'react';
import CreateNewPost from './FeedsMainPanel/CreateNewPost';
import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel';
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel'

const Feeds = ({ leftNavOpened, width }) => {

    const [showCreatePostModal, setShowCreatePostModal] = useState(false);

    return (
        <>
            <div className='FRSC h100' id='FeedsMain' width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} >
                {showCreatePostModal && <CreateNewPost onMinimize={() => setShowCreatePostModal(false)} />}
                <FeedsMainPanel />
                <FeedsRightPanel setShowCreatePostModal={setShowCreatePostModal} />
            </div>
        </>
    )
}

export default Feeds