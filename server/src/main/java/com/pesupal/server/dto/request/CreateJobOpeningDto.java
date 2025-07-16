package com.pesupal.server.dto.request;

import com.pesupal.server.enums.JobType;
import com.pesupal.server.model.recruit.JobOpening;
import com.pesupal.server.model.recruit.JobOpeningCriteria;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CreateJobOpeningDto {

    private String title;

    private String description;

    private String location;

    private Long budget;

    private LocalDateTime openTill;

    private Integer positionsAvailable;

    private JobType jobType;

    private Map<String, String> criteria;


    public JobOpening toJobOpening() {

        JobOpening jobOpening = new JobOpening();
        jobOpening.setTitle(this.getTitle());
        jobOpening.setDescription(this.getDescription());
        jobOpening.setLocation(this.getLocation());
        jobOpening.setBudget(this.getBudget());
        jobOpening.setOpenTill(this.getOpenTill());
        jobOpening.setPositionsAvailable(this.getPositionsAvailable());
        jobOpening.setJobType(this.getJobType());
        jobOpening.setCriteria(this.getCriteria().entrySet().stream().map(entry -> new JobOpeningCriteria(entry.getKey(), entry.getValue())).toList());
        return jobOpening;
    }
}
