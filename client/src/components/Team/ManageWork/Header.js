import { useEffect, useState } from 'react'
import './Header.css'
import FilterComponentItem from './FilterComponentItem';
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { toggleFilterBox } from '../../../store/reducers/ModuleFilterSlice';

const GetParams = () => {
    const { moduleId, view } = useParams();
    return { moduleId, view };
}

const CreateButtons = () => {

    const navigate = useNavigate();
    const { moduleId } = GetParams();

    const createModuleHandler = (e) => {
        e.stopPropagation();
        navigate('/manage/module/create');
    }

    const createRecordHandler = (e) => {
        e.stopPropagation();
        navigate(`/manage/module/${moduleId}/create`);
    }

    return (
        <div className='FRCE' id='createButtons' onClick={createRecordHandler}>
            <div className='FRCC mR10' id='createRecord'>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Record</span>
            </div>
            <div className='FRCC' id='createModule' onClick={createModuleHandler}>
                <i className='fa fa-plus pR5 w_20'></i>
                <span>Create Module</span>
            </div>
        </div>
    )
}

const ModuleBuilder = () => {

    const navigate = useNavigate();
    const { moduleId } = GetParams();

    const clickHandler = (e) => {
        e.stopPropagation();
        navigate(`/manage/module/builder/${moduleId}`);
    }

    return (
        <div className="FRCC mL10" id='moduleBuilderIcon' title='Module Builder' onClick={clickHandler}>
            <i className='fa fa-cog'></i>
        </div>
    )
}

const FilterIcon = () => {

    const [filterApplied, setFilterApplied] = useState(false);
    const { filterBoxShowing } = useSelector((state) => state.moduleFilter);
    const dispatch = useDispatch();

    const toggleFilterHandler = (e) => {
        e.stopPropagation();
        dispatch(toggleFilterBox());
    }

    return (
        <div className={`FRCC mR10 ${filterBoxShowing ? 'active' : ''}`} title='Filter' id='filterIcon' onClick={toggleFilterHandler}>
            <i className='fa fa-filter'></i>
            {filterApplied && <i className='fa fa-circle' style={{ fontSize: '8px', position: 'absolute', top: '-2px', right: '-2px', color: 'red' }}></i>}
        </div>
    )
}

const ModulesList = ({ modules }) => {

    const navigate = useNavigate();
    const { moduleId, view } = GetParams();

    const onChange = (e) => {
        const moduleId = e.target.value;
        const route = `/manage/module/${moduleId}/${view || 'list'}`;
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

    const { moduleId, view: currentView } = GetParams();

    const onChange = (e) => {
        const view = e.target.value;
        const route = "/manage/module/" + moduleId + "/" + view;
        navigate(route);
    }

    return (
        <FilterComponentItem item={{ icon: 'fa fa-list', options: views }} onChange={onChange} selectedValue={currentView} />
    )
}

const Header = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [modules, setModules] = useState([]);

    const { moduleId, view } = GetParams();

    const isCreateRecordPage = view === 'create';

    useEffect(() => {
        apiRequest("/api/v1/module/all", "GET").then(({ data }) => {
            setModules(data);
            if (data.length > 0 && !moduleId?.length) {
                const { id } = data[0] || {};
                navigate(`/manage/module/${id}/${view || 'list'}`);
            }
        }).catch(({ message }) => {
            dispatch(showPopup({ message, type: 'error' }));
        });
    }, []);

    return isCreateRecordPage ? null : (
        <div id='task_header' className='FCSS w100'>
            <div className='FRCB w100 mB10'>
                {/* <CustomModules CustomModulesList={CustomModulesList} /> */}
                <div className='FRCS' id='modulesList-filter-view'>
                    <ModulesList modules={modules} />
                    <FilterIcon />
                    <ViewsList />
                    <ModuleBuilder />
                </div>
                <CreateButtons />
            </div>
        </div>
    )
}

export default Header