package com.pesupal.server.controller;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDto> saveMedia(@RequestPart("file") MultipartFile file) throws IOException {

        UUID mediaId = mediaService.saveMedia(file);
        return ResponseEntity.ok(new ApiResponseDto("Media saved successfully.", Map.of("id", mediaId)));
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<Resource> getMedia(@PathVariable UUID mediaId) throws MalformedURLException, FileNotFoundException {

        // Get content type from header
        String contentType = RequestContext.get("CONTENT-TYPE", String.class);
        Resource resource = mediaService.getResourceById(mediaId, contentType);
        return ResponseEntity.ok().contentType(MediaType.valueOf(contentType)).body(resource);
    }
}
