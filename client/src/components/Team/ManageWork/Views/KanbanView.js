import React from 'react'
import { Link } from 'react-router-dom';
import './KanbanView.css'

const RowComponent = ({ item }) => {

    const { title, route, tag, priority, owner, draggable } = item,
        getPriortyColorAndIcon = (priority) => {
            switch (priority) {
                case 'High':
                    return { priorityColor: 'red', priorityIcon: 'fa-solid fa-bolt' }
                case 'Medium':
                    return { priorityColor: 'orange', priorityIcon: 'fa-solid fa-circle-exclamation' }
                case 'Low':
                    return { priorityColor: 'green', priorityIcon: 'fa-solid fa-gamepad' }
                default:
                    return { priorityColor: 'black', priorityIcon: 'fa-solid fa-circle-exclamation' }
            }
        },
        getTagColor = (tag) => {
            switch (tag) {
                case 'Bug':
                    return 'red';
                case 'Feature':
                    return 'skyblue';
                case 'Task':
                    return 'magenta';
                default:
                    return 'black';
            }
        },
        { priorityColor, priorityIcon } = getPriortyColorAndIcon(priority),
        tag_color = getTagColor(tag),
        { ownerImage, ownerName } = owner;

    return <Link to={route} className='kanbanviewItem' draggable={draggable}>
        {tag && <div className="tag" style={{ backgroundColor: tag_color }}>{tag}</div>}
        <p className='mB10 kanbanviewItemTitle'>{title}</p>
        <div className='FRCB creator_owner_div'>
            <div className='FRCS ownerDiv'>
                <img src={ownerImage} className='img_20_20' alt="edit" />
                <span className='mL5 color777' style={{ fontSize: '13px' }}>{ownerName}</span>
            </div>
            <div className='priority FRCE'>
                <i className={priorityIcon} style={{ color: priorityColor }}></i>
                <span className='mL5' >{priority}</span>
            </div>
        </div>
    </Link>

};

const ColumnComponent = ({ column }) => {
    const { status, items } = column,
        itemCount = items?.length || 0;
    return <div className='kanbanviewColumn FCSS'>
        <div className='kanbanviewStage FRCB'>
            <span className='alignCenter'>{status}</span>
            <span className='columnCount'>{itemCount}</span>
        </div>
        <div className='FCSS kanbanviewItems noScrollbar'>
            {
                items.length ?
                    items.map((item, index) => <RowComponent item={item} key={index} />) :
                    <div className='w100 alignCenter colorAAA selectNone h100 FRCC'>No records</div>
            }
        </div>
    </div>

}

const KanbanView = ({ ManageWorkListKanban }) => {
    return (
        <div id='KanbanviewFrame' className='FRSS'>
            {ManageWorkListKanban.map((column, index) => <ColumnComponent column={column} key={index} />)}
        </div>
    )
}

export default KanbanView