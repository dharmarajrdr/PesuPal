import React from 'react'
import './Header.css'
import CustomModules from './CustomModules'
import CustomModulesList from './CustomModulesList';
import FilterComponent from './FilterComponent';
import FiltersList from './FiltersList'
import SearchComponent from './SearchComponent';

const CreateButtons = () => {

    return (
        <div className='FRCC' id='createButtons'>
            <div className='FRCC mR10' id='createModule'>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Module</span>
            </div>
            <div className='FRCC' id='createRecord'>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Record</span>
            </div>
        </div>
    )
}

const Header = () => {
    return (
        <div id='task_header' className='FCSS w100'>
            <div className='FRCB w100 mB10'>
                <CustomModules CustomModulesList={CustomModulesList} />
                <CreateButtons />
            </div>
            <div className='mT5 mB10 FRCB w100'>
                <FilterComponent FiltersList={FiltersList} />
                <SearchComponent />
            </div>
        </div>
    )
}

export default Header