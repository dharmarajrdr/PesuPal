import './DeleteOrg.css';

const LeaveOrg = () => {
    return (
        <div id='leave-org' className='w100'>
            <h3 id='title'>Leave Organization</h3>
            <p id='description' className='mT10'>Are you sure you want to leave this organization? Once you leave, you will lose access to all the data and resources associated with it.</p>
            <div className='FCSS mB10'>
                <span className='warnings'>1. If you are the owner of the organization, you must transfer ownership before leaving.</span>
                <span className='warnings'>2. You will lose access to all the data and resources associated with this organization.</span>
                <span className='warnings'>3. You can rejoin the organization only if you are invited again by an existing member.</span>
            </div>
            <button id='leave-org-button' className='mT5'>
                <i className='fa fa-sign-out w15 mR5' aria-hidden="true"></i>
                Leave Organization
            </button>
        </div>
    )
}

export default LeaveOrg