import './DeleteOrg.css';

const DeleteOrg = () => {

    return (
        <div id='delete-org' className='w100'>
            <h3 id='title'>Delete Organization</h3>
            <p id='description' className='mT10'>Once you delete an organization, there is no going back. Please be certain.</p>
            <div className='FCSS mB10'>
                <span className='warnings'>1. All the data associated with this organization will be permanently deleted.</span>
                <span className='warnings'>2. Users will no longer be able to sign in to this organization.</span>
                <span className='warnings'>3. You will lose access to this organization and all the resources associated with it.</span>
            </div>
            <button id='delete-org-button' className='mT5'>
                <i className='fa fa-trash w15 mR5' aria-hidden="true"></i>
                Delete Organization
            </button>
        </div>
    )
}

export default DeleteOrg