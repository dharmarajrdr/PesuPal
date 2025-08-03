package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.service.interfaces.module.RecordRelationService;

public abstract class RecordRelationServiceImpl implements RecordRelationService {

    /**
     * If any pre-creation logic is needed, override this method in the subclass.
     *
     * @param moduleField
     */
    public void beforeFieldCreation(ModuleField moduleField) {

    }

    /**
     * Mostly all service implementations will be same. So, created a default implementation.
     * If any service needs different implementation, override this method in the subclass.
     *
     * @param moduleField
     * @param addModuleFieldDto
     * @return
     */
    public ModuleFieldDto storeInitialValuesOnFieldsCreation(ModuleField moduleField, AddModuleFieldDto addModuleFieldDto) {

        return ModuleFieldDto.fromModuleField(moduleField);
    }
}
