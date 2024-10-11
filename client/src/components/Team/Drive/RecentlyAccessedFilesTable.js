import React from 'react'
import './RecentlyAccessedFilesTable.css'
import utils from '../../../utils';
import RecentlyAccessedFilesList from './RecentlyAccessedFilesList';

const Col = ({ item }) => {
    const { data, width, category, option_icon } = item,
        { icon, icon_color } = utils.getIconBasedOnCategory(category);
    return <td className='FRCS' style={{ width }} >
        {category && <i className={icon + " img_20_20 mR10"} style={{ color: icon_color }} ></i>}
        <span>{data}</span>
        {option_icon && <i className={option_icon + " color777"}></i>}
    </td>
}

const Row = ({ row }) => {
    return <tr className='FRCB body_row'>
        {row.map((item, index) => {
            return <Col item={item} key={index} />
        })}
    </tr>
}

const RecentlyAccessedFilesTableItem = ({ body }) => {
    return <>
        {body.map((row, index) =>
            <Row row={row} key={index} />
        )}
    </>
}

const RecentlyAccessedFilesTable = () => {
    const { title, header, body } = RecentlyAccessedFilesList;
    return (
        <div id='RecentlyAccessedFilesTable' className='w100'>
            <h3 className='fs14 mb10'>{title}</h3>
            <div id='table' className='noScrollbar'>
                <tr className='FRCB' id='header'>
                    {header.map(({ title, sort, width }, index) =>
                        <td style={{ width }} key={index}>
                            <b className='fw400'>{title}</b>
                            {sort && <i className={utils.getIconForSorting(sort) + ' mL10'}></i>}
                        </td>
                    )}
                </tr>
                <RecentlyAccessedFilesTableItem body={body} />
            </div>
        </div>
    )
}

export default RecentlyAccessedFilesTable