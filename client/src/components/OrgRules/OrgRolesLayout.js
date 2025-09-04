import './OrgRolesLayout.css';
import OrgRolesTable from './OrgRolesTable';
import OrgRolesHeader from './OrgRolesHeader';

const OrgRolesLayout = () => {

    return (
        <div id='org-roles-layout' className='w100'>
            <div id='org-roles-content'>
                <OrgRolesHeader />
                <OrgRolesTable />
            </div>
        </div>
    )
}

export default OrgRolesLayout