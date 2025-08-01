package com.pesupal.server.factory;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.service.interfaces.module.RecordRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordSelectRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordStringRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordUserRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory class to get the appropriate RecordRelationService based on the field type.
 * This class is used to encapsulate the logic of selecting the correct service implementation for handling different types of record relations.
 */
@Component
@AllArgsConstructor
public class RecordRelationFactory {

    private final RecordUserRelationService recordUserRelationService;
    private final RecordSelectRelationService recordSelectRelationService;
    private final RecordStringRelationService recordStringRelationService;

    public RecordRelationService getRelationService(FieldType fieldType) {

        switch (fieldType) {
            case USER -> {
                return recordUserRelationService;
            }
            case SELECT -> {
                return recordSelectRelationService;
            }
            case STRING, EMAIL, TEXT -> {
                return recordStringRelationService;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }
}
