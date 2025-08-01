import './FilterComponent.css';

const FilterComponentItem = ({ item, onChange, selectedValue }) => {

    const { icon, options } = item || {};

    return (
        <div className='FilterComponentItem p5 FRCC'>
            <i className={icon + ' img_40_40 alignCenter'}></i>
            <select onChange={onChange} value={selectedValue}>
                {options?.map(({ id, name }, index) => <option key={index} value={id}>{name}</option>)}
            </select>
        </div>
    )
}

export default FilterComponentItem