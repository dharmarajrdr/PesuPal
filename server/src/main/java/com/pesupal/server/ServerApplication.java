package com.pesupal.server;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    static {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
        System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
        System.setProperty("AWS_S3_ACCESS_KEY", dotenv.get("AWS_S3_ACCESS_KEY"));
        System.setProperty("AWS_S3_SECRET_KEY", dotenv.get("AWS_S3_SECRET_KEY"));
        System.setProperty("AWS_S3_REGION", dotenv.get("AWS_S3_REGION"));
        System.setProperty("AWS_S3_BUCKET_NAME", dotenv.get("AWS_S3_BUCKET_NAME"));
        System.setProperty("STRIPE_API_KEY", dotenv.get("STRIPE_API_KEY"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
