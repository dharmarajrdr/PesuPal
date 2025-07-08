package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateDesignationDto;
import com.pesupal.server.dto.request.UpdateDesignationDto;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.projections.DesignationProjection;

import java.util.List;

public interface DesignationService {

    Designation createDesignation(CreateDesignationDto createDesignationDto);

    Designation getDesignationById(Long id);

    List<DesignationProjection> getAllDesignations(Long orgId);

    Designation updateDesignation(Long id, UpdateDesignationDto updateDesignationDto);
}
