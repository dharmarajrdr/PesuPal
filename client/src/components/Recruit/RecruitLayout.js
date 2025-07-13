import React from 'react'
import JobApplications from './JobApplications'
import ListOfJobOpenings from './ListOfJobOpenings'

const RecruitLayout = () => {
  return (
    <div id='recruit-layout'>
        <JobApplications ListOfJobOpenings={ListOfJobOpenings} />
    </div>
  )
}

export default RecruitLayout