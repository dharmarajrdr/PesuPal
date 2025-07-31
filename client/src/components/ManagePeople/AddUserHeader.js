import React from 'react'
import './AddUserHeader.css';

const AddUserHeader = () => {
    return (
        <div id='add-user-header' className='FCSC w100'>

            <div id='user-cover-image-wrapper' className='w100'>
                <img src='https://colorfully.eu/wp-content/uploads/2012/06/beautiful-nature-landscape-road-facebook-cover.jpg' className='w100' />
            </div>
            <div className='FRCS w100' id='user-display-picture-div-wrapper'>
                <div id='display-picture-wrapper'>
                    <img src='https://in.bmscdn.com/iedb/artist/images/website/poster/large/sivakarthikeyan-1042969-18-09-2017-03-37-23.jpg' />
                </div>
            </div>

        </div>
    )
}

export default AddUserHeader