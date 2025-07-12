package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.JobOpeningStatus;
import com.pesupal.server.enums.JobType;
import com.pesupal.server.model.recruit.JobOpening;
import com.pesupal.server.model.recruit.JobOpeningCriteria;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobOpeningDto {

    private String title;

    private String description;

    private String location;

    private LocalDateTime openTill;

    private JobOpeningStatus status;

    private JobType jobType;

    private List<JobOpeningCriteria> criteria;

    public static JobOpeningDto fromJobOpening(JobOpening jobOpening) {

        JobOpeningDto jobOpeningDto = new JobOpeningDto();
        jobOpeningDto.setTitle(jobOpening.getTitle());
        jobOpeningDto.setDescription(jobOpening.getDescription());
        jobOpeningDto.setLocation(jobOpening.getLocation());
        jobOpeningDto.setOpenTill(jobOpening.getOpenTill());
        jobOpeningDto.setStatus(jobOpening.getStatus());
        jobOpeningDto.setJobType(jobOpening.getJobType());
        jobOpeningDto.setCriteria(jobOpening.getCriteria());
        return jobOpeningDto;
    }
}
