package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.WebsitePreviewDto;

import java.io.IOException;

public interface WebService {

    public WebsitePreviewDto getWebsitePreview(String url) throws IOException;
}
