package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateDesignationDto;
import com.pesupal.server.dto.request.org.UpdateDesignationDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.Designation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.DesignationProjection;
import com.pesupal.server.repository.org.DesignationRepository;
import com.pesupal.server.service.interfaces.org.DesignationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DesignationServiceImpl extends CurrentValueRetriever implements DesignationService {

    private final DesignationRepository designationRepository;

    /**
     * Creates a new designation.
     *
     * @param createDesignationDto
     * @return Designation
     */
    @Override
    public Designation createDesignation(CreateDesignationDto createDesignationDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Org org = orgMember.getOrg();

        Boolean designationExists = designationRepository.existsByNameAndOrgId(createDesignationDto.getDesignation().getName(), org.getId());

        if (designationExists) {
            throw new DuplicateDataReceivedException("Designation '" + createDesignationDto.getDesignation().getName() + "' already exists.");
        }

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

    /**
     * Saves a designation entity.
     *
     * @param designation
     * @return
     */
    @Override
    public Designation save(Designation designation) {

        return designationRepository.save(designation);
    }
}
