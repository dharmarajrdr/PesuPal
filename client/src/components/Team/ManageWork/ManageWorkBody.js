import React from 'react'
import './ManageWorkBody.css'
import KanbanView from './Views/KanbanView'
import ManageWorkList from './ManageWorkList';
import ListView from './Views/ListView';

const ManageWorkBody = () => {
    return (
        <div id='ManageWorkBody' className='w100 custom-scrollbar'>
            {/* <KanbanView ManageWorkList={ManageWorkList} /> */}
            <ListView ManageWorkList={ManageWorkList} />
        </div>
    )
}

export default ManageWorkBody