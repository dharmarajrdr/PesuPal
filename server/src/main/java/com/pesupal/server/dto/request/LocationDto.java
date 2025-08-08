package com.pesupal.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {

    private Double latitude;

    private Double longitude;
}
