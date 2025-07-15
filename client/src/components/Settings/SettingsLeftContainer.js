import ListOfSettingNavigations from './ListOfSettingNavigations';
import '../Team/TeamLeftContainer.css';
import SettingsLeftContainerItem from './SettingsLeftContainerItem';

const SettingsLeftContainer = ({ leftNavigationState, width }) => {

    const [leftNavOpened, setLeftNavOpened] = leftNavigationState,
        openCloseLeftNav = () => setLeftNavOpened(!leftNavOpened);

    return (
        <div id='TeamLeftContainer' className='FCSS p10 custom-scrollbar' style={{ width }}>
            {/* <div id='openCloseLeftNavigationContainer' className='w100 FRCE'>
                <i className={"fa-solid fa-angles-" + (leftNavOpened ? 'left' : 'right')} onClick={openCloseLeftNav}></i>
            </div> */}
            {ListOfSettingNavigations.map((item, index) => <SettingsLeftContainerItem key={index} item={item} leftNavOpened={leftNavOpened} />)}
        </div>
    )
}

export default SettingsLeftContainer