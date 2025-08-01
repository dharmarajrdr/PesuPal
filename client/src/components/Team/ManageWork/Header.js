import { useEffect, useState } from 'react'
import './Header.css'
import FilterComponentItem from './FilterComponentItem';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';

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
        <div className='FRCC mR10' id='filterIcon'>
            <i className='fa fa-filter'></i>
            {filterApplied && <i className='fa fa-circle' style={{ fontSize: '8px', position: 'absolute', top: '-2px', right: '-2px', color: 'red' }}></i>}
        </div>
    )
}

const ModulesList = ({ modules }) => {

    const navigate = useNavigate();
    const params = useParams();
    const [moduleId, view] = params['*'].split('/') || [];

    const onChange = (e) => {
        const moduleId = e.target.value;
        const route = `/team/manage_work/${moduleId}/${view || 'list'}`;
        navigate(route);
    }

    return <span className='mR10'>
        <FilterComponentItem item={{ id: 1, title: 'Module 1', icon: 'fa fa-chart-bar', options: modules }} onChange={onChange} selectedValue={moduleId} />
    </span>
}

const ViewsList = () => {

    const navigate = useNavigate();

    const views = [
        { id: "list", name: 'List View' },
        { id: "kanban", name: 'Kanban View' }
    ];

    const { view: currentView, moduleId: currentModuleId } = useSelector((state) => state.currentModule);

    const onChange = (e) => {
        const view = e.target.value;
        const route = "/team/manage_work/" + currentModuleId + "/" + view;
        navigate(route);
    }

    return (
        <FilterComponentItem item={{ icon: 'fa fa-list', options: views }} onChange={onChange} selectedValue={currentView} />
    )
}

const Header = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const params = useParams();
    const [modules, setModules] = useState([]);

    const [moduleId, view] = params['*'].split('/') || [];

    useEffect(() => {
        apiRequest("/api/v1/module/all", "GET").then(({ data }) => {
            setModules(data);
            if (data.length > 0 && !moduleId?.length) {
                const { id } = data[0] || {};
                navigate(`/team/manage_work/${id}/${view || 'list'}`);
            }
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return (
        <div id='task_header' className='FCSS w100'>
            <div className='FRCB w100 mB10'>
                {/* <CustomModules CustomModulesList={CustomModulesList} /> */}
                <div className='FRCS' id='modulesList-filter-view'>
                    <ModulesList modules={modules} />
                    <FilterIcon />
                    <ViewsList />
                </div>
                <CreateButtons />
            </div>
        </div>
    )
}

export default Header