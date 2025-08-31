package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.WebsitePreviewDto;
import com.pesupal.server.service.interfaces.WebService;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class WebServiceImpl implements WebService {

    /**
     * Fetches a preview of a website given its URL.
     *
     * @param url
     * @return
     */
    @Override
    public WebsitePreviewDto getWebsitePreview(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        String title = doc.select("meta[property=og:title]").attr("content");
        if (title.isEmpty()) {
            title = doc.title();
        }

        String description = doc.select("meta[property=og:description]").attr("content");
        if (description.isEmpty()) {
            description = doc.select("meta[name=description]").attr("content");
        }

        String image = doc.select("meta[property=og:image]").attr("content");

        return new WebsitePreviewDto(title, description, image);
    }
}
