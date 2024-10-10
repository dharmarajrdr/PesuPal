import React, { useEffect } from 'react'
import './DriveUsageStats.css';

import { Chart } from "react-google-charts";


const DriveUsageStats = () => {

    const usageStats_data = [
        ["Task", "Hours per Day"],
        ["Free space", 20],
        ["Images", 9],
        ["Videos", 5],
        ["Documents", 3],
        ["Audio", 2]
    ], usageStats_options = {
        title: "Usage stats",
        pieHole: 0.5,
        is3D: false,
        slices: [
            { color: "#999" }, { color: "#42aa1b" }, { color: "#ff7bff" }, { color: "#8081ff" }, { color: "#fa23ac" }
        ],
        legend: {
            position: "bottom",
            alignment: "center",
            textStyle: {
                color: "#233238",
                fontSize: 14
            },
            maxLines: 3
        },
        tooltip: {
            showColorCode: true
        },
        chartArea: { left: "10%", top: 0, width: "80%", height: "100%" },
    };

    return (
        <div id='DriveUsageStats'>

            <div style={{ width: '100%', height: '250px' }}>
                <Chart chartType="PieChart" data={usageStats_data} options={usageStats_options} width={"100%"} height={"250px"} />
            </div>
            <p className='w100 mT20 alignCenter color555' style={{ fontSize: '12px' }}>Shows the usage stats of the drive.</p>

        </div>
    )
}

export default DriveUsageStats