import './GroupVisibility.css';

const GroupVisibility = ({ isPublic, setIsPublic, groupVisibilityEditable }) => {

    groupVisibilityEditable = groupVisibilityEditable !== undefined ? groupVisibilityEditable : true;

    return <div className="visibility-toggle-container w100">
        <label className="toggle-label">Visibility</label>
        <div className="segmented-toggle">
            <div className={`toggle-option ${isPublic ? 'active' : ''}`} onClick={() => groupVisibilityEditable ? setIsPublic(true) : null}>
                <i className="fa fa-globe" />
                <span className='mL5'>Open to all</span>
            </div>
            <div className={`toggle-option ${!isPublic ? 'active' : ''}`} onClick={() => groupVisibilityEditable ? setIsPublic(false) : null}>
                <i className="fa fa-lock" />
                <span className='mL5'>Closed</span>
            </div>
            <div className={`toggle-bg ${isPublic ? 'left' : 'right'}`} />
        </div>
    </div>
}

export default GroupVisibility