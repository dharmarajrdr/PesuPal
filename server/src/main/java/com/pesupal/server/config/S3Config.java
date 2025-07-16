package com.pesupal.server.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    Dotenv dotenv = Dotenv.load();

    private final String accessKey = dotenv.get("aws.s3.accessKey");

    private final String secretKey = dotenv.get("aws.s3.secretKey");

    private final String region = dotenv.get("aws.s3.region");

    @Bean
    public S3Client s3Client() {

        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Client.builder().region(Region.of(region)).credentialsProvider(credentialsProvider).build();
    }

    @Bean
    public S3Presigner s3Presigner() {

        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Presigner.builder().region(Region.of(region)).credentialsProvider(credentialsProvider).build();
    }
}
