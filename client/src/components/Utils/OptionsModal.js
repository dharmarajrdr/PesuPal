import './OptionsModal.css';

const OptionsModal = ({ options, style }) => {

    return (
        <div id='options-container' className='FCCS' style={style}>
            {options?.map(({ icon, name, count, onClick }, index) => (name && icon) ? (
                <div key={index} className='option FRCS w100 cursP' onClick={onClick}>
                    <i className={icon} />
                    <span className='option-name'>{name}</span>
                    {count > 0 && <span className='option-count'>{count > 9 ? '9+' : count}</span>}
                </div>
            ) : null)}
        </div>
    )
}

export default OptionsModal