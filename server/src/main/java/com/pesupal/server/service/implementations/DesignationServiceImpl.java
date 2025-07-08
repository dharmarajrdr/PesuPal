package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateDesignationDto;
import com.pesupal.server.dto.request.UpdateDesignationDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.projections.DesignationProjection;
import com.pesupal.server.repository.DesignationRepository;
import com.pesupal.server.service.interfaces.DesignationService;
import com.pesupal.server.service.interfaces.OrgService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final OrgService orgService;

    /**
     * Creates a new designation.
     *
     * @param createDesignationDto
     * @return Designation
     */
    @Override
    public Designation createDesignation(CreateDesignationDto createDesignationDto) {

        Boolean designationExists = designationRepository.existsByNameAndOrgId(createDesignationDto.getDesignation().getName(), createDesignationDto.getOrgId());

        if (designationExists) {
            throw new DuplicateDataReceivedException("Designation '" + createDesignationDto.getDesignation().getName() + "' already exists.");
        }

        Org org = orgService.getOrgById(createDesignationDto.getOrgId());
        Designation designation = createDesignationDto.getDesignation();
        designation.setOrg(org);
        return designationRepository.save(designation);
    }

    /**
     * Retrieves a designation by its ID.
     *
     * @param id
     * @return Designation
     */
    @Override
    public Designation getDesignationById(Long id) {

        return designationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Designation not found with id: " + id));
    }

    /**
     * Retrieves all designations for a given organization.
     *
     * @param orgId
     * @return List<DesignationProjection>
     */
    @Override
    public List<DesignationProjection> getAllDesignations(Long orgId) {

        return designationRepository.findAllByOrgId(orgId);
    }

    /**
     * Updates an existing designation.
     *
     * @param id
     * @param updateDesignationDto
     * @return Designation
     */
    @Override
    public Designation updateDesignation(Long id, UpdateDesignationDto updateDesignationDto) {

        Designation designation = getDesignationById(id);
        updateDesignationDto.copy(designation);
        return designationRepository.save(designation);
    }
}
