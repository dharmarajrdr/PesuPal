import './Role.css';

const Role = ({ person }) => {

    const { department, designation } = person || {};

    return <div className='FCCC role-column'>
        <p>{designation || 'N/A'}</p>
        <p>{department || 'N/A'}</p>
    </div>
}

export default Role;