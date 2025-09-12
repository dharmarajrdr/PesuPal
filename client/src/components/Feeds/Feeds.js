import { useState } from 'react';
import FeedsHeader from './FeedsHeader';
import FeedsMainPanel from './FeedsMainPanel/FeedsMainPanel';
import FeedsRightPanel from './FeedsRightPanel/FeedsRightPanel';

const Feeds = ({ leftNavOpened, width }) => {

    const [searchText, setSearchText] = useState('');

    return <div className='FCSC h100 pR' id='FeedsMain' width={leftNavOpened ? `calc(100% - ${width.leftNavOpened})` : `calc(100% - ${width.leftNavClosed})`} >
        <FeedsHeader searchText={searchText} setSearchText={setSearchText} />
        <div className='FRSC w100' id='FeedsBody'>
            <FeedsMainPanel searchText={searchText} />
            <FeedsRightPanel />
        </div>
    </div>
}

export default Feeds