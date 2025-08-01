import './FilterComponent.css';

const FilterComponentItem = ({ item, onChange, selectedValue }) => {

    const { id, title, icon, dom, options } = item;

    return (
        <div className='FilterComponentItem p5 FRCC'>
            <i className={icon + ' img_40_40 alignCenter'}></i>
            <select onChange={onChange} value={selectedValue}>
                {options.map((option, index) => <option key={index} value={option.id}>{option.title}</option>)}
            </select>
        </div>
    )
}

export default FilterComponentItem