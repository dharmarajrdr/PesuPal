import Loader from '../../Loader';
import { useEffect, useState } from 'react';
import DirectoryPath from './DirectoryPath';
import { apiRequest } from '../../../http_request';
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from 'react-router-dom';
import PreviewFolderFileList from './PreviewFolderFileList'
import { showPopup } from '../../../store/reducers/PopupSlice';
import { setFolderId, setItems, setSpace } from '../../../store/reducers/DriveSlice';

const spaces = ['org_space', 'team_space', 'personal_space'];

const InsideDirectory = () => {
    
    const params = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [loader, setLoader] = useState(true);
    const items = useSelector((state) => state.drive.items) || [];
    const folders = items.filter(item => item.type === 'FOLDER');
    const files = items.filter(item => item.type === 'FILE');

    useEffect(() => {

        const { space, folderId } = params || {};
        const findAllFilesAndFolders = folderId ? `/api/v1/workdrive/folders/${folderId}` : `/api/v1/workdrive/${space.toUpperCase()}/folders`;

        if (!spaces.includes(space)) {
            dispatch(showPopup({ message: `'${space}' does not exist.`, type: 'error' }));
            return navigate('/store/personal_space');
        }

        dispatch(setSpace(space.toUpperCase()));
        dispatch(setFolderId(folderId || null));

        apiRequest(findAllFilesAndFolders, 'GET').then(({ data }) => {
            setLoader(false);
            dispatch(setItems(data));
        }).catch(({ message }) => {
            setLoader(false);
            dispatch(setItems([]));
            dispatch(showPopup({ message, type: 'error' }));
        });

    }, [params.folderId, params.space]);

    return (
        <div className='mT10 w100'>
            {params.folderId && <DirectoryPath />}
            <div className='FCSS mT20 w100' id='previews_stats'>
                {loader ? <Loader /> :
                    <>
                        {folders.length > 0 && <PreviewFolderFileList items={folders} title={"Folders"} />}
                        {files.length > 0 && <PreviewFolderFileList items={files} title={"Files"} />}
                    </>
                }
            </div>
        </div>
    )
}

export default InsideDirectory