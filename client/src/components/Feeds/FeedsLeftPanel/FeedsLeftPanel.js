import './FeedsLeftPanel.css'
import FeedsLeftPanelList from './FeedsLeftPanelList'
import FeedsLeftPanelItem from './FeedsLeftPanelItem'

const FeedsLeftPanel = ({ leftNavigationState, width }) => {

    const [leftNavOpened, setLeftNavOpened] = leftNavigationState;
    const openCloseLeftNav = () => setLeftNavOpened(!leftNavOpened);

    return (
        <div id='FeedsLeftPanel' className='h100 custom-scrollbar' style={{ width }}>
            <div id='openCloseLeftNavigationContainer' className='w100 FRCE'>
                <i className={"fa-solid fa-angles-" + (leftNavOpened ? 'left' : 'right')} onClick={openCloseLeftNav}></i>
            </div>
            {FeedsLeftPanelList.map((item, index) => <FeedsLeftPanelItem key={index} item={item} leftNavOpened={leftNavOpened} />)}
        </div>
    )
}

export default FeedsLeftPanel