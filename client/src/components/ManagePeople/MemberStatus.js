import './MemberStatus.css';

const MemberStatus = ({ archived }) => {

    return <div className={`FRCS member-status ${archived ? 'inactive' : 'active'}`}>
        <i className={`fa fa-circle pR5 w20`} />
        <span>{archived ? 'Inactive' : 'Active'}</span>
    </div>
}

export default MemberStatus;