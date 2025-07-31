package com.pesupal.server.factory;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.service.interfaces.module.RecordRelationService;
import com.pesupal.server.service.interfaces.module.relation.RecordUserRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecordRelationFactory {

    private final RecordUserRelationService recordUserRelationService;

    public RecordRelationService getRelationService(FieldType fieldType) {

        switch (fieldType) {
            case USER -> {
                return recordUserRelationService;
            }
        }
        return null;
    }
}
