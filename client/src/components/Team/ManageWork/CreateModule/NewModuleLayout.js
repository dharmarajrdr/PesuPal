import CreateModuleForm from './CreateModuleForm';
import CreateModuleHeader from './CreateModuleHeader';
import './NewModuleLayout.css';

const NewModuleLayout = () => {
  return (
    <div id='new-module-creation-layout' className='Layout'>
      <CreateModuleHeader />
      <CreateModuleForm />
    </div>
  )
}

export default NewModuleLayout