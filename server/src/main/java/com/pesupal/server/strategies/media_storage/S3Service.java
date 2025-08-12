package com.pesupal.server.strategies.media_storage;

import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.service.interfaces.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Service
public class S3Service implements MediaService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Uploads a file to the S3 bucket.
     *
     * @param file
     * @throws Exception
     */
    @Override
    public MediaUploadDto uploadFile(MultipartFile file) throws Exception {

        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        UUID mediaId = UUID.randomUUID();
        String fileNameWithExtension = mediaId + "." + extension;
        Long size = file.getSize();

        s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(fileNameWithExtension).build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return MediaUploadDto.builder().mediaId(mediaId).extension(extension).size(size).build();
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

    /**
     * Generates a pre-signed URL for accessing a file in the S3 bucket.
     *
     * @param key
     * @return
     */
    @Override
    public URL generatePresignedUrl(String key) {

        String redisKey = "s3-presigned-url:" + key;
        String cachedPresignedUrl = (String) redisTemplate.opsForValue().get(redisKey);
        Duration TTL = Duration.ofMinutes(5);

        if (cachedPresignedUrl != null) {
            try {
                return URI.create(cachedPresignedUrl).toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5)).getObjectRequest(getObjectRequest).build();
        URL presignedUrl = s3Presigner.presignGetObject(presignRequest).url();
        redisTemplate.opsForValue().set(redisKey, presignedUrl, TTL);
        return presignedUrl;
    }

    /**
     * Deletes a file from the S3 bucket.
     *
     * @param key
     */
    @Override
    public void deleteFile(String key) {

        s3Client.deleteObject(software.amazon.awssdk.services.s3.model.DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
    }
}
