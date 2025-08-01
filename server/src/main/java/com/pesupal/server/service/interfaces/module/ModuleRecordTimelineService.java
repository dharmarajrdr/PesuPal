package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.ModuleRecordTimeline;

import java.util.List;

public interface ModuleRecordTimelineService {

    void createTimeLine(ModuleRecordTimeline moduleRecordTimeline);

    List<ModuleRecordTimeline> getModuleRecordTimelinesByRecordId(String moduleRecordId);

    void deleteByModuleRecord(ModuleRecord moduleRecord);

    void deleteAllByModule(Module module);
}
