import React from 'react'
import './FilterComponent.css'

const FilterComponentItem = ({ item }) => {
    const { id, title, icon, dom, options } = item;
    return (
        <div className='FilterComponentItem p5 FRCC'>
            <i className={icon + ' img_40_40 alignCenter'}></i>
            <select defaultValue="All">
                {options.map((option, index) => <option key={index} value={option.id}>{option.title}</option>)}
            </select>
        </div>
    )
}

const SaveClearFilter = () => {
    return (
        <>
            <div className='FRCC mR10 mL10' id='createRecord' style={{ backgroundColor: 'green' }}>
                <span>Save Filter</span>
            </div>
            <div className='FRCC' id='createRecord' style={{ backgroundColor: 'white', border: '1px solid red' }}>
                <span style={{ color: 'red' }}>Clear Filter</span>
            </div>
        </>
    )
}

const FilterComponent = ({ FiltersList }) => {

    return (
        <div id='FilterComponent' className='W100 FRCS noScrollbar'>
            {FiltersList.map((item, index) => <FilterComponentItem key={index} item={item} />)}
            {/* <SaveClearFilter /> */}
        </div>
    )
}

export default FilterComponent