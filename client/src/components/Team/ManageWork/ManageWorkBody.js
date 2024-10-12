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
                <Route path="" element={<Navigate to="/team/manage_work/list" />} />
                <Route path='list' element={<ListView ManageWorkList={ManageWorkList} />} />
                <Route path='kanban' element={<KanbanView ManageWorkListKanban={ManageWorkListKanban} />} />
                {/* Wildcard route to catch all unmatched routes */}
                <Route path="*" element={<Navigate to="/team/manage_work/list" />} />
            </Routes>
        </div>
    )
}

export default ManageWorkBody