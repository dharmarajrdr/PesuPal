const ManagePeopleHeader = ({ setShowAddUserLayout, searchTerm, setSearchQuery }) => {

    return (
        <div className='FRCB w100' id='manage-people-header'>
            <input className="search-input" placeholder="Search people..." value={searchTerm} onChange={(e) => setSearchQuery(e.target.value)} />
            <div className="FRCE">
                <i className="fa fa-envelope" id="invitation-icon" title="Invitations" />
                <button className="add-btn" onClick={() => setShowAddUserLayout(true)}><i className="fa fa-user-plus colorFFF w20 pR5" />Add User</button>
            </div>
        </div>
    )
}

export default ManagePeopleHeader