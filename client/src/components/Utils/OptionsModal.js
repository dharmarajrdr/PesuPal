import './OptionsModal.css';

const OptionsModal = ({ options, style }) => {

    return (
        <div id='options-container' className='FCCS' style={style}>
            {options.map(({ icon, name, onClick }, index) => (name && icon) ? (
                <div key={index} className='option FRCS w100' onClick={onClick}>
                    <i className={icon} />
                    <span className='option-name'>{name}</span>
                </div>
            ) : null)}
        </div>
    )
}

export default OptionsModal