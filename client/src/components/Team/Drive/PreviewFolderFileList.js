import './PreviewFolderFileList.css'
import utils from '../../../utils';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';

const PreviewFolderFileListItem = ({ item }) => {

    const { space } = useSelector((state) => state.drive);
    let { id, type, name, size, files, extension, owner, security } = item;

    const { displayName: ownerName } = owner || {};
    const isSecured = security == 'SECURED' && space != 'PERSONAL_SPACE';

    if (type == 'FOLDER') {
        extension = 'folder';
    }

    const { icon, color } = utils.getIconByFileExtension(extension || '');

    const route = `/store/${space.toLowerCase()}/${type.toLowerCase()}/${id}`;

    return <Link to={route} className='FCSS PreviewFolderFileListItem p10 cursP'>
        <div className='FRSB w100 mb5'>
            <div className='FRSS icon_foldername'>
                <i className={'mR10 alignCenter w15 fa ' + icon} style={{ color }}></i>
                <span className='color333 folderName FRCS'>{name}{isSecured && <i className='fa fa-lock mL5 color777' style={{ fontSize: '10px' }}></i>}</span>
            </div>
            <i className='fas fa-ellipsis-v color777' style={{ fontSize: '14px' }}></i>
        </div>
        <div className='FRSS pT5 overflowHidden w100'>
            <span className='fs10 mR5 color777'>{ownerName}</span>
            {files > 0 && <span className='fs10 mR5 bL_line color777'>{files || 0} Files</span>}
            {size > 0 && <span className='fs10 mR5 color777 bL_line'>{utils.formatFileSize(size || 0)}</span>}
        </div>
    </Link>

}

const PreviewFolderFileList = ({ title, subHeadings, items }) => {
    return (
        <div id='PreviewFolderFileList' className='FCSS w100 pB10'>
            <div className='FRCB w100 mb5'>
                <h3 className='fs14 mb10 FRCC'>{title} <span className='fs12 color555 fw400 mL5'>({items.length})</span></h3>
                {/* <div className='FRSS mb10'>
                    {subHeadings.map(({ title, active }, index) => <span key={index} className={'subHeading color777 mL15 fs12 cursP ' + (active ? 'active' : null)}>{title}</span>)}
                </div> */}
            </div>
            <div id='list_of_items' className='pB10 FRSS noScrollbar'>
                {items.map((item, index) => <PreviewFolderFileListItem key={index} item={item} />)}
            </div>
        </div>
    )
}

export default PreviewFolderFileList