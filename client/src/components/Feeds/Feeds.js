import FeedsLeftPanel from './FeedsLeftPanel/FeedsLeftPanel'
import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel'
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel'

const Feeds = ({ leftNavigationState, leftNavOpened, width }) => {
    return (
        <>
            <FeedsLeftPanel leftNavigationState={leftNavigationState} width={leftNavOpened ? width.leftNavOpened : width.leftNavClosed} />
            <div className='FRSC h100' id='FeedsMain' width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} >
                <FeedsMainPanel />
                <FeedsRightPanel />
            </div>
        </>
    )
}

export default Feeds