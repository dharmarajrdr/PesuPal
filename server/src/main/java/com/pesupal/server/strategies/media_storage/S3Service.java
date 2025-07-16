package com.pesupal.server.strategies.media_storage;

import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.service.interfaces.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service implements MediaService {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    /**
     * Uploads a file to the S3 bucket.
     *
     * @param file
     * @throws Exception
     */
    @Override
    public MediaUploadDto uploadFile(MultipartFile file) throws Exception {

        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.'));
        String fileName = UUID.randomUUID() + extension;
        Long size = file.getSize();

        s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return MediaUploadDto.builder().name(fileName).extension(extension).size(size).build();
    }

    /**
     * Downloads a file from the S3 bucket.
     *
     * @param key
     * @return
     */
    @Override
    public byte[] downloadFile(String key) {

        return s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        ).asByteArray();
    }
}
