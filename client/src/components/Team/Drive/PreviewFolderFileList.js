import React from 'react'
import './PreviewFolderFileList.css'

const PreviewFolderFileListItem = ({ item }) => {
    const { id, title, size, members, files, category } = item,
        icon = {
            "icon": "fas fa-question",
            "icon_color": "#555"
        }

    switch (category) {
        case "Document": {
            icon.icon = "fas fa-file-alt"
            icon.icon_color = "#ff7bff"
            break;
        }
        case "Video": {
            icon.icon = "fas fa-video"
            icon.icon_color = "#ff7bff"
            break;
        }
        case "Audio": {
            icon.icon = "fas fa-music"
            icon.icon_color = "#ff7bff"
            break;
        }
        case "Image": {
            icon.icon = "fas fa-file-image"
            icon.icon_color = "#ff7bff"
            break;
        }
        case "Folder": {
            icon.icon = "fas fa-folder"
            icon.icon_color = "#8081ff"
            break;
        }
    }

    return <div className='FCSS PreviewFolderFileListItem mR10 p10 cursP'>
        <div className='FRSB w100 mb5'>
            <div className='FRSS icon_foldername'>
                <i className={'mR10 alignCenter img_20_20 ' + icon.icon} style={{ color: icon.icon_color }}></i>
                <span className='color333 folderName'>{title}</span>
            </div>
            <i className='fas fa-ellipsis-v color777' style={{ fontSize: '14px' }}></i>
        </div>
        <div className='FRSS pT5'>
            <span className='fs10 mR5 color777'>{size}</span>
            <span className='fs10 mR5 color777 bL_line'>{members} Members</span>
            {files && <span className='fs10 mR5 bL_line color777'>{files} Files</span>}
        </div>
    </div>

}

const PreviewFolderFileList = ({ item }) => {
    const { title, subHeadings, items, icon, icon_color } = item;
    return (
        <div id='PreviewFolderFileList' className='FCSS p20'>
            <div className='FRCB w100 mb5'>
                <h3 className='fs14 mb10'>{title}</h3>
                <div className='FRSS mb10'>
                    {subHeadings.map(({ title, active }, index) => <span key={index} className={'subHeading color777 mL15 fs12 cursP ' + (active ? 'active' : null)}>{title}</span>)}
                </div>
            </div>
            <div id='list_of_items' className='pB10 noScrollbar'>
                <div className='FRSS' id='list_of_items_frame'>
                    {items.map((item, index) => <PreviewFolderFileListItem key={index} item={item} />)}
                </div>
            </div>
        </div>
    )
}

export default PreviewFolderFileList