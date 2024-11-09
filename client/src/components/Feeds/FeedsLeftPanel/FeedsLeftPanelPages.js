import React, { useState } from 'react'
import './FeedsLeftPanel.css'
import PagesList from './PagesList'
import FeedsLeftPanelPagesItem from './FeedsLeftPanelPagesItem'

const FeedsLeftPanelPages = ({ leftNavOpened }) => {
    const [toggleState, setToggleState] = useState('show'),
        [toggleIcon, setToggleIcon] = useState('fas fa-chevron-up'),
        toggleHandler = () => {
            if (toggleState === 'show') {
                setToggleState('hide')
                setToggleIcon('fas fa-chevron-down')
            } else {
                setToggleState('show')
                setToggleIcon('fas fa-chevron-up')
            }
        }
    return (
        <>
            {
                PagesList.length > 0 ?
                    <>
                        <h6 id='subtitle' className='FRCB cursP selectNone' onClick={toggleHandler}>
                            <span>Pages ({PagesList.length})</span>
                            <i className={toggleIcon}></i>
                        </h6>
                        {
                            toggleState === 'show' ?
                                PagesList.map((item, index) => <FeedsLeftPanelPagesItem key={index} item={item} leftNavOpened={leftNavOpened} />)
                                :
                                null
                        }
                    </>
                    :
                    null
            }
        </>
    )
}

export default FeedsLeftPanelPages