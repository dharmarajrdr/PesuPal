import React from 'react'
import './LeftNavigation.css'
import ListOfNavigations from './ListOfNavigations'
import Nav from './Nav'

const LeftNavigation = () => {
    return (
        <div id='LeftNavigation' className='FCCB'>
            <div className='w100'>
                {ListOfNavigations.top.map((navigation, index) => {
                    return (
                        <Nav key={index} icon={navigation.icon} title={navigation.title} route={navigation.route} />
                    )
                })}
            </div>
            <div className='w100'>
                {ListOfNavigations.bottom.map((navigation, index) => {
                    return (
                        <Nav key={index} icon={navigation.icon} title={navigation.title} route={navigation.route} />
                    )
                })}
            </div>
        </div>
    )
}

export default LeftNavigation