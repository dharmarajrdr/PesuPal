import React from 'react'
import './JobApplications.css';
import utils from '../../utils';

const JobApplication = ({ job }) => {

    const { title, candidatesCount, description, active, location, status: jobStatus, jobType, criteria, createdBy, createdAt } = job;
    const { displayName, displayPicture } = createdBy || {};

    return (
        <div className={`job-application FCSS w100 ${active ? 'active' : ''}`}>
            <div className='subject-applied-count FRCB w100'>
                <h5>{title}</h5>
                <p>
                    <i className='fa fa-user mR5' />
                    {candidatesCount > 1000 ? `${999}+` : candidatesCount}
                </p>
            </div>
            <div className='job-application-details FRCB w100'>
                <div className='job-application-created-by FRCS w100'>
                    <img src={displayPicture} className='mR5' />
                    <span>{displayName}</span>
                </div>
                {/* <span className={`job-application-status w100 ${jobStatus.toLowerCase()}`}>{jobStatus.replace('_', ' ')}</span> */}
                <div className='job-application-created-at FRCE w100'>
                    <i class="fa-solid fa-clock-rotate-left mR5"></i>
                    <span>{utils.agoTimeCalculator(new Date(createdAt))}</span>
                </div>
            </div>
        </div>
    )
}

const JobApplications = ({ ListOfJobOpenings }) => {
    return (
        <div id='job-applications' className='FCSS h100'>
            <div id='job-applications-header' className='FCSS p10 w100'>
                <h2>Job Applications</h2>
            </div>
            {ListOfJobOpenings.map((job, index) => (
                <JobApplication key={index} job={job} />
            ))}
        </div>
    )
}

export default JobApplications;