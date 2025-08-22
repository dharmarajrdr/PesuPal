import './AddUserLayout.css'
import AddUserHeader from './AddUserHeader';
import AddUserBody from './AddUserBody';
import AddUserFooter from './AddUserFooter';

const AddUserLayout = ({ setShowAddUserLayout }) => {
    const closeLayout = (e) => {
        e.stopPropagation();
        if (e.target.id !== 'add-user-layout') {
            setShowAddUserLayout(false);
        }
    };
    return (
        <div className='entire-screen-overlay' onClick={closeLayout}>
            <div id='add-user-layout' className='centerMe FCCB'>
                <AddUserHeader />
                <AddUserBody />
                <AddUserFooter />
            </div>
        </div>
    )
}

export default AddUserLayout