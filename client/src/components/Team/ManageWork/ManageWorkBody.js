import React from 'react'
import './ManageWorkBody.css'
import KanbanView from './Views/KanbanView'
import ManageWorkListKanban from './ManageWorkListKanban';
import ListView from './Views/ListView';
import ManageWorkList from './ManageWorkList';

const ManageWorkBody = ({ viewState }) => {
    const [view] = viewState;
    return (
        <div id='ManageWorkBody' className='w100 custom-scrollbar'>
            {
                view == 'list' ?
                    <ListView ManageWorkList={ManageWorkList} />
                    :
                    view == 'kanban' ?
                        <KanbanView ManageWorkListKanban={ManageWorkListKanban} /> : null
            }
        </div>
    )
}

export default ManageWorkBody