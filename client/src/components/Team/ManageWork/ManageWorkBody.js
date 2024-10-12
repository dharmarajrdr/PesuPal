import React from 'react'
import './ManageWorkBody.css'
import KanbanView from './Views/KanbanView'
import ManageWorkListKanban from './ManageWorkListKanban';
import ListView from './Views/ListView';
import ManageWorkList from './ManageWorkList';

const ManageWorkBody = () => {
    return (
        <div id='ManageWorkBody' className='w100 custom-scrollbar'>
            {/* <KanbanView ManageWorkListKanban={ManageWorkListKanban} /> */}
            <ListView ManageWorkList={ManageWorkList} />
        </div>
    )
}

export default ManageWorkBody