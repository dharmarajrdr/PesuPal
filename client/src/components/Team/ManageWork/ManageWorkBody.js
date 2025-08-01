import React from 'react'
import './ManageWorkBody.css'
import KanbanView from './Views/KanbanView'
import ManageWorkListKanban from './ManageWorkListKanban';
import ListView from './Views/ListView';
import ManageWorkList from './ManageWorkList';
import { Navigate, Route, Routes } from 'react-router-dom';

const ManageWorkBody = () => {
    return (
        <div id='ManageWorkBody' className='w100 custom-scrollbar'>
            <Routes>
                <Route path='/module/:moduleId/list' element={<ListView ManageWorkList={ManageWorkList} />} />
                <Route path='/module/:moduleId/kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                <Route path='/module/:moduleId/*' element={<Navigate to="/manage/module" />} />
                {/* Wildcard route to catch all unmatched routes */}
                {/* <Route path="*" element={<Navigate to="/manage/module/list" />} /> */}
            </Routes>
        </div>
    )
}

export default ManageWorkBody