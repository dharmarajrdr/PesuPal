import React from 'react'
import './ManageWorkLayout.css'
import Header from './Header'
import ManageWorkBody from './ManageWorkBody'
import { useState } from 'react';

const ManageWorkLayout = () => {
    const viewState = useState('list');
    return (
        <div id='ManageWorkLayout'>
            <Header viewState={viewState} />
            <ManageWorkBody viewState={viewState} />
        </div>
    )
}

export default ManageWorkLayout