import { Routes, Route, Navigate } from 'react-router-dom';
import KanbanView from './Views/KanbanView'
import ListView from './Views/ListView';
import FilterContainer from './FilterContainer/FilterContainer';

const DataView = ({ filterBoxShowing, ManageWorkList, ManageWorkListKanban }) => {

    return (
        <div id='manage-work-slider' className={`FRSE ${filterBoxShowing ? 'filter-enabled' : ''}`}>

            <FilterContainer />

            <div id='views-render-frame'>
                <Routes>
                    <Route path='/list' element={<ListView ManageWorkList={ManageWorkList} />} />
                    <Route path='/kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                    <Route path='/*' element={<Navigate to="/manage/module" />} />
                </Routes>
            </div>
        </div>
    );
};

export default DataView;
