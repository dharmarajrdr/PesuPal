package com.pesupal.server.factory;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.service.interfaces.module.RecordRelationService;
import com.pesupal.server.service.interfaces.module.relation.*;
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
    private final RecordLinkRelationService recordLinkRelationService;
    private final RecordFileRelationService recordFileRelationService;
    private final RecordPhoneRelationService recordPhoneRelationService;
    private final RecordSelectRelationService recordSelectRelationService;
    private final RecordStringRelationService recordStringRelationService;
    private final RecordDateTimeRelationService recordDateTimeRelationService;

    public RecordRelationService getRelationService(FieldType fieldType) {

        switch (fieldType) {
            case USER -> {
                return recordUserRelationService;
            }
            case SELECT -> {
                return recordSelectRelationService;
            }
            case FILE -> {
                return recordFileRelationService;
            }
            case LINK -> {
                return recordLinkRelationService;
            }
            case DATE_TIME -> {
                return recordDateTimeRelationService;
            }
            case STRING, EMAIL, TEXT -> {
                return recordStringRelationService;
            }
            case PHONE -> {
                return recordPhoneRelationService;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + fieldType);
    }
}
