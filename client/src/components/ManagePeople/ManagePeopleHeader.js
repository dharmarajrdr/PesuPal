const ManagePeopleHeader = ({ setShowAddUserLayout, searchTerm, setSearchQuery }) => {

    return (
        <div className='FRCB w100' id='manage-people-header'>
            <input className="search-input" placeholder="Search people..." value={searchTerm} onChange={(e) => setSearchQuery(e.target.value)} />
            <button className="add-btn" onClick={() => setShowAddUserLayout(true)}><i className="fa fa-user-plus colorFFF pR5" />Add User</button>
        </div>
    )
}

export default ManagePeopleHeader