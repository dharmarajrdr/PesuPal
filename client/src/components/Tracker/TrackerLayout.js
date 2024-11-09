import React from 'react'
import OveralPerformanceChart from './KPI/OveralPerformanceChart'
import PerformanceChart from './KPI/PerformanceChart'
import './TrackerLayout.css'

const TrackerLayout = () => {
    return (
        <div id='TrackerLayout' className='h100 w100 FCCC'>
            <div id='header' className='w100'>
                <h1>Tracker Layout</h1>
            </div>
            <div className='row w100 FRSB'>
                <div className='FRCB KPI_group'>
                    <OveralPerformanceChart />
                    <OveralPerformanceChart />
                </div>
                <div className='w100 h100P'>
                    <PerformanceChart />
                </div>
            </div>
            <div className='row w100 FRSB'>
                <PerformanceChart />
                <PerformanceChart />
            </div>
        </div>
    )
}

export default TrackerLayout