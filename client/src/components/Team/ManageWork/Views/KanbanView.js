import React, { useEffect } from 'react'
import { Link } from 'react-router-dom';
import utils from '../../../../utils';
import './KanbanView.css'
import { setCurrentModuleView } from '../../../../store/reducers/CurrentModuleViewSlice';
import { useDispatch } from 'react-redux';

const RowComponent = ({ item }) => {

    const { title, route, tag, priority, owner, draggable } = item,
        { icon_color: priority_color, icon } = utils.getPriortyColorAndIcon(priority),
        { icon_color: tag_color } = utils.getIconForTagWithColor(tag),
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
                <i className={icon} style={{ color: priority_color }}></i>
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

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(setCurrentModuleView("kanban"));
    }, []);

    return (
        <div id='KanbanviewFrame' className='FRSS'>
            {ManageWorkListKanban.map((column, index) => <ColumnComponent column={column} key={index} />)}
        </div>
    )
}

export default KanbanView