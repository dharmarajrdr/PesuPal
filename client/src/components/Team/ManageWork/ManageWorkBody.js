import './ManageWorkBody.css'
import ManageWorkListKanban from './ManageWorkListKanban';
import ManageWorkList from './ManageWorkList';
import { Route, Routes } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { closeFilterBox, showFilterBox } from '../../../store/reducers/ModuleFilterSlice';
import PageNotFound from '../../Auth/PageNotFound';
import DataView from './DataView';

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
            <Routes>
                <Route path='/:moduleId/create' element={<PageNotFound />} />
                <Route path='/:moduleId/*' element={<DataView filterBoxShowing={filterBoxShowing} ManageWorkList={ManageWorkList} ManageWorkListKanban={ManageWorkListKanban} />} />
            </Routes>
        </div>
    )
}

export default ManageWorkBody