import './OrgMemberList.css'
import { useState } from 'react'

const FirstChar = ({ displayName }) => {

    return <p className='first-char-of-name'>{displayName.charAt(0).toUpperCase()}</p>
}

const Person = ({ person }) => {

    const { displayName, email, displayPicture } = person;
    const [showDisplayPicture, setShowDisplayPicture] = useState(displayPicture !== null && displayPicture !== undefined);

    return <div className='FRCS person-column'>
        {showDisplayPicture ? (
            <img src={displayPicture} onError={() => setShowDisplayPicture(false)} />
        ) : <FirstChar displayName={displayName} />}
        <div className='FCSS name-and-email'>
            <p className='person-name'>{displayName}</p>
            <p className='person-email'>{email}</p>
        </div>
    </div>
}
const MemberStatus = ({ archived }) => {

    return <div className={`FRCS member-status ${archived ? 'inactive' : 'active'}`}>
        <i className={`fa fa-circle pR5 w20`} />
        <span>{archived ? 'Inactive' : 'Active'}</span>
    </div>
}

const Role = ({ person }) => {

    const { department, designation } = person || {};

    return <div className='FCCC role-column'>
        <p>{designation || 'N/A'}</p>
        <p>{department || 'N/A'}</p>
    </div>
}

const Actions = () => {

    return <div className='FRCE action-btns'>
        <span className='action-btn edit'><i className="fa fa-pencil w15" /> Edit</span>
    </div>
}

const EmployeeId = ({ employeeId }) => {

    return <div className='FRCS employee-id-column'>
        <i className='fa fa-hashtag w15 fs12 color999' />
        <p className='color999 fs14'>{employeeId}</p>
    </div>
}

const Row = ({ person }) => {

    const { id, employeeId, archived } = person || {};

    return (
        <div className='row FRCS' key={id}>
            <div className='column'><EmployeeId employeeId={employeeId} /></div>
            <div className='column'><Person person={person} /></div>
            <div className='column'><MemberStatus archived={archived} /></div>
            <div className='column'><Role person={person} /></div>
            <div className='column'><Actions /></div>
        </div>
    )
}

const NoMembersFound = () => {

    return <div className='FCCC w100 h100P' id='no-data-found'>
        <p className='FRCC w100'>
            <i className='fa fa-user w15 mR5' />
            No members found
        </p>
        <p className='w100 alignCenter'>Start adding members to this org.</p>
    </div>
}

const OrgMemberList = ({ members }) => {

    return (
        <div id='org-member-list'>
            <div className='w100' id='org-member-table' cellPadding={0} cellSpacing={0}>

                <div className='row header w100 FRCS'>
                    <div className='column'>ID</div>
                    <div className='column'>Person</div>
                    <div className='column'>Status</div>
                    <div className='column'>Role</div>
                    <div className='column'></div>
                </div>

                {members.length ? members.map((person, index) => <Row key={index} person={person} />) : <NoMembersFound />}

            </div>
        </div>
    )
}

export default OrgMemberList