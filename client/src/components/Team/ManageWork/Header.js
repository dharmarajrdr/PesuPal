import { useEffect, useState } from 'react'
import './Header.css'
import FilterComponentItem from './FilterComponentItem';
import { useNavigate, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';

const CreateButtons = () => {

    return (
        <div className='FRCE' id='createButtons'>
            <div className='FRCC mR10' id='createModule'>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Module</span>
            </div>
            <div className='FRCC' id='createRecord'>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Record</span>
            </div>
        </div>
    )
}

const FilterIcon = () => {
    const [filterApplied, setFilterApplied] = useState(false);
    return (
        <div className='FRCC mL10 mR10' id='filterIcon'>
            <i className='fa fa-filter'></i>
            {filterApplied && <i className='fa fa-circle' style={{ fontSize: '8px', position: 'absolute', top: '-2px', right: '-2px', color: 'red' }}></i>}
        </div>
    )
}

const ModulesList = () => {
    const [modules, setModules] = useState([
        { id: 1, title: "Bugs" },
        { id: 2, title: "Tasks" },
        { id: 3, title: "Projects" },
        { id: 4, title: "Issues" }
    ]);
    return (
        <FilterComponentItem item={{ id: 1, title: 'Module 1', icon: 'fa fa-chart-bar', dom: 'module1', options: modules }} />
    )
}

const ViewsList = () => {

    const navigate = useNavigate();

    const [views, setViews] = useState([
        { id: "list", title: 'List View' },
        { id: "kanban", title: 'Kanban View' }
    ]);

    const currentView = useSelector((state) => state.currentModuleView);

    useEffect(() => {
        console.log("Update View:", currentView);
    }, []);

    console.log("Current View:", currentView);

    const onChange = (e) => {
        const view = e.target.value;
        const route = "/team/manage_work/" + view;
        navigate(route);
    }

    return (
        <FilterComponentItem item={{ icon: 'fa fa-list', options: views }} onChange={onChange} selectedValue={currentView} />
    )
}

const Header = () => {
    return (
        <div id='task_header' className='FCSS w100'>
            <div className='FRCB w100 mB10'>
                {/* <CustomModules CustomModulesList={CustomModulesList} /> */}
                <div className='FRCS' id='modulesList-filter-view'>
                    <ModulesList />
                    <FilterIcon />
                    <ViewsList />
                </div>
                <CreateButtons />
            </div>
        </div>
    )
}

export default Header