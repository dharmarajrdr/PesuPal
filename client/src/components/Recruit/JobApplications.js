import { useEffect, useState } from 'react'
import './JobApplications.css';
import utils from '../../utils';
import { apiRequest } from '../../http_request';
import Loader from '../Loader';
import ErrorMessage from '../ErrorMessage';

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
                    <i className="fa-solid fa-clock-rotate-left mR5"></i>
                    <span>{utils.agoTimeCalculator(new Date(createdAt))}</span>
                </div>
            </div>
        </div>
    )
}

const NoJobApplications = () => {

    return (
        <div className='FCCC w100 h100' id='no-data-found'>
            <p className='FRCC w100'>
                <i className='fa fa-briefcase mR5' />
                No job applications found.
            </p>
            <p className='w100 alignCenter'>Start creating a new job opening.</p>
        </div>
    )
}

const JobApplications = () => {

    const [ListOfJobOpenings, setListOfJobOpenings] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        apiRequest('/api/v1/job-opening', 'GET').then(({ data }) => {
            setLoading(false);
            setListOfJobOpenings(data);
        }).catch((error) => {
            setLoading(false);
            setError(error.message);
        });

    }, []);

    return (
        <div id='job-applications' className='FCSS h100'>
            <div id='job-applications-header' className='FCSS p10 w100'>
                <h2>Job Applications</h2>
            </div>
            {
                loading ? <Loader message='Fetching job applications...' /> :
                    error ? <ErrorMessage message={error} /> :
                        ListOfJobOpenings.length ? ListOfJobOpenings.map((job, index) => (
                            <JobApplication key={index} job={job} />
                        )) : <NoJobApplications />
            }
        </div>
    )
}

export default JobApplications;
