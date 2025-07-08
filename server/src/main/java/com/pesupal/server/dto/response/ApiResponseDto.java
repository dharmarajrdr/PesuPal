package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.ResponseStatus;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto {

    private String message;

    private Object data;

    private ResponseStatus status;

    public ApiResponseDto(String message, Object data, ResponseStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public ApiResponseDto(String message, Object data) {
        this.message = message;
        this.data = data;
        this.status = ResponseStatus.SUCCESS;
    }

    public ApiResponseDto(String message) {
        this.message = message;
        this.data = null;
        this.status = ResponseStatus.SUCCESS;
    }

    public ApiResponseDto(String message, ResponseStatus status) {
        this.message = message;
        this.data = null;
        this.status = status;
    }
}
