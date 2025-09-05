import './NewRulesLayout.css';

const NewRulesLayout = ({ onCloseNewRoleLayout }) => {
    return (
        <div className='entire-screen-overlay' onClick={onCloseNewRoleLayout}>
            <div id='new-rules-content' className='centerMe'>

            </div>
        </div>
    )
}

export default NewRulesLayout