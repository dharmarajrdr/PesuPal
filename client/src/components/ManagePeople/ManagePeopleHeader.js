import { useEffect, useState } from "react"
import OptionsModal from "../Utils/OptionsModal";
import { useNavigate, useParams } from "react-router";

const ManagePeopleHeader = ({ setShowAddUserLayout, searchTerm, setSearchQuery }) => {

    const navigate = useNavigate();
    const { '*': currentOption } = useParams();
    const [isOptionOpen, setIsOptionOpen] = useState(false);
    const [selectedOption, setSelectedOption] = useState(null);
    const options = [
        {
            name: `All Members`,
            icon: `fa fa-users`,
            routePath: 'all-members',
            selected: selectedOption?.name === 'All Members',
            onClick: (e) => {
                e.stopPropagation();
                navigate('/settings/manage-people/all-members');
                setIsOptionOpen(false);
            }
        },
        {
            name: `Super Admins`,
            icon: `fa fa-user-shield`,
            routePath: 'super-admins',
            selected: selectedOption?.name === 'Super Admins',
            onClick: (e) => {
                e.stopPropagation();
                navigate('/settings/manage-people/super-admins');
                setIsOptionOpen(false);
            }
        },
        {
            name: `Pending Invites`,
            icon: `fa fa-user-clock`,
            routePath: 'pending-invites',
            selected: selectedOption?.name === 'Pending Invites',
            count: 5,
            onClick: (e) => {
                e.stopPropagation();
                navigate('/settings/manage-people/pending-invites');
                setIsOptionOpen(false);
            }
        },
        {
            name: `Pending Role Assignments`,
            icon: `fa fa-user-tag`,
            routePath: 'pending-role-assignments',
            selected: selectedOption?.name === 'Pending Role Assignments',
            count: 2000,
            onClick: (e) => {
                e.stopPropagation();
                navigate('/settings/manage-people/pending-role-assignments');
                setIsOptionOpen(false);
            }
        },
        {
            name: `Inactive Members`,
            icon: `fa fa-user-slash`,
            routePath: 'inactive-members',
            selected: selectedOption?.name === 'Inactive Members',
            onClick: (e) => {
                e.stopPropagation();
                navigate('/settings/manage-people/inactive-members');
                setIsOptionOpen(false);
            }
        }
    ]

    useEffect(() => {
        for (let option of options) {
            if (option.routePath === currentOption) {
                setSelectedOption(option);
                return;
            }
        }
        setSelectedOption(options[0]);
    }, [currentOption]);

    return (
        <div className='FRCB w100' id='manage-people-header'>
            <input className="search-input" placeholder="Search people..." value={searchTerm} onChange={(e) => setSearchQuery(e.target.value)} />
            <div className="FRCE">
                <i className={selectedOption?.icon} id="more-options-icon" onClick={() => setIsOptionOpen(!isOptionOpen)} ></i>
                {isOptionOpen && <OptionsModal options={options} style={{ top: '65px', right: '145px' }} />}
                <button className="add-btn" onClick={() => setShowAddUserLayout(true)}><i className="fa fa-user-plus colorFFF w20 pR5" />Add User</button>
            </div>
        </div>
    )
}

export default ManagePeopleHeader