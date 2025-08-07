### Job Openings Table

| id | title                   | description                                 | location  | budget  | created_at | open_till  | hiring_incharge | status | positions_available | job_type  |
|----|-------------------------|---------------------------------------------|-----------|---------|------------|------------|-----------------|--------|:-------------------:|-----------|
| 1  | Software Engineer       | Develop and maintain software applications. | Bangalore | 2200000 | 2023-10-01 | 2023-10-07 | Damini Gupta    | open   |          2          | full-time |
| 2  | SDE-2 Backend Developer | Design and implement backend systems.       | Chennai   | 2500000 | 2023-10-02 | 2023-10-09 | Kartick         | draft  |          6          | full-time |

### Job Openings Criteria Table

| id | job_id | criteria_type | criteria_value |
|----|--------|---------------|----------------|
| 1  | 1      | Experience    | 3+ years       |
| 2  | 1      | Skills        | Java, Spring   |
| 3  | 1      | Degree        | B.E or B.Tech  |

### Candidates Table

| id | user_id | name     | job_id | resume_id                            | created_at | referral_by | status                  |
|----|---------|----------|--------|--------------------------------------|------------|-------------|-------------------------|
| 1  | 101     | Lohith R | 1      | a5fc88ea-6d5d-45fe-abd9-0a6449253b06 | 2023-10-05 | Dharmaraj R | Rejected by Candidate   |
| 2  | 102     | Priya S  | 2      | b7fc88ea-6d5d-45fe-abd9-0a6449253b07 | 2023-10-06 |             | Shortlisted             |
| 3  | 103     | Arjun K  | 1      | c8fc88ea-6d5d-45fe-abd9-0a6449253b08 | 2023-10-07 |             | Interviewing            |
| 4  | 104     | Sneha P  | 1      | d9fc88ea-6d5d-45fe-abd9-0a6449253b09 | 2023-10-08 |             | Under Review            |
| 5  | 104     | Sneha P  | 2      | d9fc88ea-6d5d-45fe-abd9-0a6449253b09 | 2023-10-08 | Dharmaraj R | On-Boarded              |
| 6  | 105     | Ravi T   | 1      | eafc88ea-6d5d-45fe-abd9-0a6449253b10 | 2023-10-09 |             | Rejected by Interviewer |

### Candidate Timeline Table

| id | candidate_id | created_at | status                                               |
|----|--------------|------------|------------------------------------------------------|
| 1  | 5            | 2023-10-05 | Sent OA Invitation                                   |
| 2  | 5            | 2023-10-06 | Cleared OA                                           |
| 3  | 5            | 2023-10-07 | Round 1 Interview Scheduled                          |
| 4  | 5            | 2023-10-08 | Cleared Round 1 and so, Scheduled Round 2            |
| 5  | 5            | 2023-10-09 | Not Satisfied with Round 2, Rejected                 |
| 6  | 5            | 2023-10-10 | Reconsidered for Round 2 Interview based on feedback |
| 7  | 5            | 2023-10-11 | Cleared Round 2, Offer Letter Sent                   |
| 8  | 5            | 2023-10-12 | Offer Accepted                                       |
