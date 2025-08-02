import './ManageWorkBody.css'
import KanbanView from './Views/KanbanView'
import ManageWorkListKanban from './ManageWorkListKanban';
import ListView from './Views/ListView';
import ManageWorkList from './ManageWorkList';
import { Navigate, Route, Routes } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { closeFilterBox, showFilterBox } from '../../../store/reducers/ModuleFilterSlice';

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
                <div id='filter-container' className='FRCC'>

                </div>
                <div id='views-render-frame'>
                    <Routes>
                        <Route path='/:moduleId/list' element={<ListView ManageWorkList={ManageWorkList} />} />
                        <Route path='/:moduleId/kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                        <Route path='/:moduleId/*' element={<Navigate to="/manage/module" />} />
                        {/* Wildcard route to catch all unmatched routes */}
                        {/* <Route path="*" element={<Navigate to="/manage/module/list" />} /> */}
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default ManageWorkBody