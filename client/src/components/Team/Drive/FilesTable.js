import React from 'react'
import './RecentlyAccessedFilesTable.css'
import utils from '../../../utils';

const Col = ({ item }) => {
    const { data, width, category, option_icon } = item,
        { icon, icon_color } = utils.getIconBasedOnCategory(category);
    return <td className='FRCS' style={{ width }} >
        {category && <i className={icon + " img_20_20 mR10"} style={{ color: icon_color }} ></i>}
        <span>{data}</span>
        {option_icon && <i className={option_icon + " color777 mR10"}></i>}
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

const FilesTable = ({ item }) => {
    const { header, body } = item;
    return <div id='table' className='noScrollbar'>
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
}

export default FilesTable