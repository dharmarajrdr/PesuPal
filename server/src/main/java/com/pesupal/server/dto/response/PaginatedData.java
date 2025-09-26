package com.pesupal.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PaginatedData<T> {

    private T data;

    private Map<String, Object> info;
}
