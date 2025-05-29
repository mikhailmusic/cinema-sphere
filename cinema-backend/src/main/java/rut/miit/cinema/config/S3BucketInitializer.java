package rut.miit.cinema.config;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Component
public class S3BucketInitializer {
    private static final Logger log = LogManager.getLogger(S3BucketInitializer.class);
    private final S3Client s3Client;
    private final String bucketName;

    public S3BucketInitializer(S3Client s3Client, @Value("${minio.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @PostConstruct
    public void createBucketIfNotExists() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
            log.info("Bucket '{}' already exists", bucketName);
        } catch (NoSuchBucketException e) {
            log.warn("Bucket '{}' not found. Creating...", bucketName);
            CreateBucketResponse response = s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            log.info("Bucket created at location: {}", response.location());
        } catch (S3Exception e) {
            log.error("Error checking/creating bucket: {}", e.awsErrorDetails().errorMessage(), e);
        }
    }
}
