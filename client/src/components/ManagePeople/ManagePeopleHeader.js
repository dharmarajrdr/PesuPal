import { useEffect, useState } from "react"
import OptionsModal from "../Utils/OptionsModal";

const ManagePeopleHeader = ({ setShowAddUserLayout, searchTerm, setSearchQuery }) => {

    const [isOptionOpen, setIsOptionOpen] = useState(false);
    const [selectedOption, setSelectedOption] = useState(null);
    const options = [
        {
            name: `All Members`,
            icon: `fa fa-users`,
            onClick: (e) => {
                e.stopPropagation();
            }
        },
        {
            name: `Super Admins`,
            icon: `fa fa-user-shield`,
            onClick: (e) => {
                e.stopPropagation();
            }
        },
        {
            name: `Pending Invites`,
            icon: `fa fa-user-clock`,
            count: 5,
            onClick: (e) => {
                e.stopPropagation();
            }
        },
        {
            name: `Pending Role Assignments`,
            icon: `fa fa-user-tag`,
            count: 2000,
            onClick: (e) => {
                e.stopPropagation();
            }
        },
        {
            name: `Inactive Members`,
            icon: `fa fa-user-slash`,
            onClick: (e) => {
                e.stopPropagation();
            }
        }
    ]

    useEffect(() => {
        if (!selectedOption) {
            setSelectedOption(options[0]);
        }
    }, [selectedOption, options]);

    return (
        <div className='FRCB w100' id='manage-people-header'>
            <input className="search-input" placeholder="Search people..." value={searchTerm} onChange={(e) => setSearchQuery(e.target.value)} />
            <div className="FRCE">
                <i className="fa fa-bars-staggered" id="more-options-icon" onClick={() => setIsOptionOpen(!isOptionOpen)} ></i>
                {isOptionOpen && <OptionsModal options={options} style={{ top: '65px', right: '145px' }} />}
                <button className="add-btn" onClick={() => setShowAddUserLayout(true)}><i className="fa fa-user-plus colorFFF w20 pR5" />Add User</button>
            </div>
        </div>
    )
}

export default ManagePeopleHeader