import './ManageWorkBody.css'
import ManageWorkListKanban from './ManageWorkListKanban';
import { Navigate, Route, Routes } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { closeFilterBox, showFilterBox } from '../../../store/reducers/ModuleFilterSlice';
import FilterContainer from './FilterContainer/FilterContainer';
import ListView from './Views/ListView';
import KanbanView from './Views/KanbanView';

const ManageWorkBody = () => {

    const { filterBoxShowing } = useSelector((state) => state.moduleFilter);
    const dispatch = useDispatch();

    useEffect(() => {
        if (filterBoxShowing) {
            dispatch(showFilterBox());
        } else {
            dispatch(closeFilterBox());
        }
    }, []);

    return (
        <div id='ManageWorkBody' className='w100 custom-scrollbar FRSE'>
            <div id='manage-work-slider' className={`FRSE ${filterBoxShowing ? 'filter-enabled' : ''}`}>

                <FilterContainer />

                <div id='views-render-frame'>
                    <Routes>
                        <Route path='/list' element={<ListView />} />
                        <Route path='/kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                        <Route path='/*' element={<Navigate to="/manage/module" />} />
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default ManageWorkBody