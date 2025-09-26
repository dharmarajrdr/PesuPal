package com.pesupal.server.dto.request;

import com.pesupal.server.enums.SortOrder;
import lombok.Data;

@Data
public class SortColumnDto {

    private String column;

    private SortOrder order;
}
