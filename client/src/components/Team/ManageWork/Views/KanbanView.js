import { useEffect } from 'react'
import { Link, useParams } from 'react-router-dom';
import utils from '../../../../utils';
import './KanbanView.css'
import { setCurrentModuleId, setCurrentModuleView } from '../../../../store/reducers/CurrentModuleSlice';
import { useDispatch } from 'react-redux';

const RowComponent = ({ item }) => {

    const { moduleId, recordId, fields } = item || {};
    const route = `/manage/module/${moduleId}/record/${recordId}`;

    const [subjectField, createdByField, createdAtField] = fields || [{}, {}, {}];
    const { data: subject } = subjectField || {};
    const { data: createdAt } = createdAtField || {};
    const { data: createdBy } = createdByField || {};
    const { id: userId, displayName, displayPicture } = createdBy || {};

    return subjectField && createdBy && createdAt && <Link to={route} className='kanbanviewItem' draggable={false}>
        <p className='mB10 kanbanviewItemTitle'>{subject}</p>
        <div className='FRCB creator_owner_div'>
            <div className='FRCS ownerDiv'>
                <img src={displayPicture} className='img_20_20' alt="edit" />
                <span className='mL5 color777' style={{ fontSize: '13px' }}>{displayName}</span>
            </div>
            <div className='priority FRCE'>
                <span className='mL5 fs10 color777' >{utils.agoTimeCalculator(createdAt)}</span>
            </div>
        </div>
    </Link>

};

const ColumnComponent = ({ column }) => {
    const { id, key, value } = column,
        { name: transitionName } = key || {},
        { data, info } = value || {},
        { totalRecords } = info || {};
    return transitionName && <div className='kanbanviewColumn FCSS'>
        <div className='kanbanviewStage FRCB'>
            <span className='alignCenter w100'>{transitionName}</span>
            <span className='columnCount' title='Total Records'>{totalRecords}</span>
        </div>
        <div className='FCSS kanbanviewItems noScrollbar'>
            {
                data?.length ? data.map((item, index) => <RowComponent item={item} key={index} />) :
                    <div className='w100 alignCenter colorAAA selectNone h100 FRCC'>No records</div>
            }
        </div>
    </div>

}

const KanbanView = ({ records }) => {

    const dispatch = useDispatch();
    const { moduleId } = useParams();

    useEffect(() => {
        dispatch(setCurrentModuleView("kanban"));
        dispatch(setCurrentModuleId(moduleId));
    }, []);

    return records.length && (
        <div id='KanbanviewFrame' className='FRSS'>
            {records.map((column, index) => <ColumnComponent column={column} key={index} />)}
        </div>
    )
}

export default KanbanView