import React from 'react'
import './Header.css'
import CustomModules from './CustomModules'
import CustomModulesList from './CustomModulesList';
import FilterComponent from './FilterComponent';
import FiltersList from './FiltersList'
import SearchComponent from './SearchComponent';

const Header = ({ viewState }) => {
    return (
        <div id='task_header' className='FCSS w100'>
            <div className='FRCB w100 mB10'>
                <CustomModules CustomModulesList={CustomModulesList} />
                <div className='FRCC' id='createRecord'>
                    <i className='fa fa-plus pR5 w_20'></i>
                    <span>Create Record</span>
                </div>
            </div>
            <div className='mT5 mB10 FRCB w100'>
                <FilterComponent FiltersList={FiltersList} viewState={viewState} />
                <SearchComponent viewState={viewState} />
            </div>
        </div>
    )
}

export default Header