import DepartmentHeader from './DepartmentHeader';
import './DepartmentLayout.css';
import DepartmentMain from './DepartmentMain';

const DepartmentLayout = () => {
    return (
        <div id='departmentLayout' className='w100 h100 FCSS'>
            <DepartmentHeader departmentId={1} />
            <DepartmentMain />
        </div>
    )
}

export default DepartmentLayout