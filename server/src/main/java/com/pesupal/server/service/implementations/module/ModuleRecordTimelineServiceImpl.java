package com.pesupal.server.service.implementations.module;

import com.pesupal.server.model.module.ModuleRecordTimeline;
import com.pesupal.server.repository.ModuleRecordTimelineRepository;
import com.pesupal.server.service.interfaces.module.ModuleRecordTimelineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    }
}
