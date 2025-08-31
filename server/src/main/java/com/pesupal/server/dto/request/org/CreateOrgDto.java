package com.pesupal.server.dto.request.org;

import lombok.Data;

@Data
public class CreateOrgDto {

    private OrgDetailsDto org;

    private UserDetailsDto user;
}
