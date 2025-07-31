package com.pesupal.server.factory;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.service.interfaces.module.RecordRelationService;
import org.springframework.stereotype.Component;

@Component
public class RecordRelationFactory {

    public RecordRelationService getRelationService(FieldType fieldType) {

        return null;
    }
}
