package com.pesupal.server.dto.response.post;

import lombok.Data;

@Data
public class QuoteDto {

    private String quote;

    private String author;

    private String category;
}
