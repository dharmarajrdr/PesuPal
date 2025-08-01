package com.pesupal.server.factory;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.service.interfaces.module.RecordRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordSelectRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordUserRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecordRelationFactory {

    private final RecordUserRelationService recordUserRelationService;
    private final RecordSelectRelationService recordSelectRelationService;

    public RecordRelationService getRelationService(FieldType fieldType) {

        switch (fieldType) {
            case USER -> {
                return recordUserRelationService;
            }
            case SELECT -> {
                return recordSelectRelationService;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }
}
