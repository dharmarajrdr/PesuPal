package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.service.interfaces.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponseDto> uploadFile(MultipartFile file) throws Exception {

        MediaUploadDto mediaUploadDto = mediaService.uploadFile(file);
        return ResponseEntity.ok(new ApiResponseDto("File uploaded successfully.", mediaUploadDto));
    }

    private String getContentType(String key) {

        return switch (key.substring(key.lastIndexOf('.') + 1).toLowerCase()) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            case "pdf" -> "application/pdf";
            case "mp4" -> "video/mp4";
            default -> "application/octet-stream";
        };
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String key) {

        byte[] fileData = mediaService.downloadFile(key);

        // Set content type based on file extension (you can also detect MIME type dynamically)
        String contentType = getContentType(key);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.inline().filename(key).build());

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @GetMapping("/download/presigned")
    public ResponseEntity<ApiResponseDto> downloadFileWithPresignedUrl(@RequestParam String key) throws Exception {

        URL presignedUrl = mediaService.generatePresignedUrl(key);
        return ResponseEntity.ok().body(new ApiResponseDto("Presigned URL generated successfully.", presignedUrl.toString()));
    }

}
