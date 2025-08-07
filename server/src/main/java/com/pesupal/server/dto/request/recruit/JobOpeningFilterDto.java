package com.pesupal.server.dto.request.recruit;

import com.pesupal.server.enums.JobOpeningStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobOpeningFilterDto {

    JobOpeningStatus status;
}
