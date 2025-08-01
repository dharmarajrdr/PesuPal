package com.pesupal.server.service.implementations.module;

import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.ModuleRecordTimeline;
import com.pesupal.server.repository.ModuleRecordTimelineRepository;
import com.pesupal.server.service.interfaces.module.ModuleRecordTimelineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModuleRecordTimelineServiceImpl implements ModuleRecordTimelineService {

    private final ModuleRecordTimelineRepository moduleRecordTimelineRepository;

    /**
     * Creates a timeline record for a module.
     *
     * @param moduleRecordTimeline
     */
    @Override
    public void createTimeLine(ModuleRecordTimeline moduleRecordTimeline) {

        moduleRecordTimelineRepository.save(moduleRecordTimeline);
    }

    /**
     * Retrieves a list of module record timelines by the record ID.
     *
     * @param moduleRecordId
     * @return
     */
    @Override
    public List<ModuleRecordTimeline> getModuleRecordTimelinesByRecordId(String moduleRecordId) {

        return moduleRecordTimelineRepository.findAllByRecord_PublicIdOrderByCreatedAt(moduleRecordId);
    }

    /**
     * Deletes all timelines associated with a specific module record.
     *
     * @param moduleRecord
     */
    @Override
    public void deleteByModuleRecord(ModuleRecord moduleRecord) {

        moduleRecordTimelineRepository.deleteAllByRecord(moduleRecord);
    }
}
