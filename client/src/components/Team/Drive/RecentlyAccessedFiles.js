import React from 'react'
import './RecentlyAccessedFilesTable.css'
import RecentlyAccessedFilesList from './RecentlyAccessedFilesList';
import FilesTable from './FilesTable';

const RecentlyAccessedFiles = () => {
    const { title } = RecentlyAccessedFilesList;
    return (
        <div id='RecentlyAccessedFiles' className='w100'>
            <h3 className='fs14 mb10'>{title}</h3>
            <FilesTable item={RecentlyAccessedFilesList} />
        </div>
    )
}

export default RecentlyAccessedFiles