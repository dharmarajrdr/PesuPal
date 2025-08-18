import { useState } from 'react';
import utils from '../../../utils';
import './PreviewFolderFileList.css'
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import OptionsModal from '../../Utils/OptionsModal';
import { hideConfirmationPopup, showConfirmationPopup } from '../../../store/reducers/ConfirmationPopupSlice';
import { apiRequest } from '../../../http_request';
import { showPopup } from '../../../store/reducers/PopupSlice';
import { removeItem } from '../../../store/reducers/DriveSlice';

const PreviewFolderFileListItem = ({ item, activeItem, setActiveItem }) => {

    const dispatch = useDispatch();
    const { space } = useSelector((state) => state.drive);
    let { id, type, name, size, files, extension, owner, security } = item;
    const showOptions = activeItem?.id == id && activeItem?.type == type;

    const { displayName: ownerName } = owner || {};
    const isSecured = security == 'SECURED' && space != 'PERSONAL_SPACE';

    if (type == 'FOLDER') {
        extension = 'folder';
    }

    const { icon, color } = utils.getIconByFileExtension(extension || '');

    const route = `/store/${space.toLowerCase()}/${type.toLowerCase()}/${id}`;

    const noLink = (e) => {
        e.preventDefault();
        e.stopPropagation();
    }

    const options = [
        {
            name: `Delete ${type == 'FOLDER' ? 'Folder' : 'File'}`,
            icon: `fa fa-trash`,
            onClick: (e) => {
                noLink(e);
                dispatch(showConfirmationPopup({
                    message: `Are you sure you want to delete this ${type.toLowerCase()}?`,
                    options: [
                        {
                            title: 'Delete',
                            color: 'red',
                            onClick: () => {
                                apiRequest(``, 'DELETE').then(({ message }) => {
                                    dispatch(removeItem({ id }));
                                    dispatch(showPopup({ message, type: 'success' }));
                                }).catch(({ message }) => {
                                    dispatch(showPopup({ message, type: 'error' }));
                                });
                            }
                        },
                        {
                            title: 'Cancel',
                            color: 'gray',
                            onClick: () => {
                                setActiveItem(null);
                                dispatch(hideConfirmationPopup());
                            }
                        }
                    ]
                }));
            }
        }
    ];

    const optionsClickHandler = (e) => {
        noLink(e);
        if (activeItem?.id == id && activeItem?.type == type) {
            return setActiveItem(null);
        }
        setActiveItem({ id, type });
    }

    return <Link to={route} className='FCSS PreviewFolderFileListItem p10 cursP'>
        <div className='FRSB w100 mb5 pR'>
            <div className='FRCS icon_foldername' title={name}>
                <i className={'mR10 alignCenter w15 fa ' + icon} style={{ color }}></i>
                <p className='color333 folderName FRCS'>
                    <span>{name}</span>
                    {isSecured && <i className='fa fa-lock mL5 color777' style={{ fontSize: '10px' }}></i>}
                </p>
            </div>
            {showOptions && <OptionsModal options={options} style={{ right: '0px', top: '20px' }} />}
            <i className='fas fa-ellipsis-v options-icon' onClick={optionsClickHandler}></i>
        </div>
        <div className='FRSS pT5 overflowHidden w100'>
            <span className='fs10 mR5 color777'>{ownerName}</span>
            {files > 0 && <span className='fs10 mR5 bL_line color777'>{files || 0} Files</span>}
            {size > 0 && <span className='fs10 mR5 color777 bL_line'>{utils.formatFileSize(size || 0)}</span>}
        </div>
    </Link>

}

const PreviewFolderFileList = ({ title, subHeadings, items }) => {

    const [activeItem, setActiveItem] = useState(null);

    return (
        <div id='PreviewFolderFileList' className='FCSS w100 pB10'>
            <div className='FRCB w100 mb5'>
                <h3 className='fs14 mb10 FRCC'>{title} <span className='fs12 color555 fw400 mL5'>({items.length})</span></h3>
                {/* <div className='FRSS mb10'>
                    {subHeadings.map(({ title, active }, index) => <span key={index} className={'subHeading color777 mL15 fs12 cursP ' + (active ? 'active' : null)}>{title}</span>)}
                </div> */}
            </div>
            <div id='list_of_items' className='pB10 FRSS noScrollbar'>
                {items.map((item, index) => <PreviewFolderFileListItem key={index} item={item} activeItem={activeItem} setActiveItem={setActiveItem} />)}
            </div>
        </div>
    )
}

export default PreviewFolderFileList