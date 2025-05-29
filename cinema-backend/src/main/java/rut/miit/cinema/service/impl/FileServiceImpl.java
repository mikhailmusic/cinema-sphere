package rut.miit.cinema.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rut.miit.cinema.dto.ImageDto;
import rut.miit.cinema.service.FileService;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LogManager.getLogger(FileServiceImpl.class);
    private final String bucketName;
    private final S3Client s3Client;

    @Autowired
    public FileServiceImpl(@Value("${minio.bucket-name}") String bucketName, S3Client s3Client) {
        this.bucketName = bucketName;
        this.s3Client = s3Client;
    }

    public String addImage(ImageDto dto) {
        String key = UUID.randomUUID() + "_" + dto.getContentType().hashCode();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("original-filename", dto.getOriginalFilename());
        s3Client.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(key).contentType(dto.getContentType()).metadata(metadata).build(),
                RequestBody.fromBytes(dto.getData())
        );

        return key;
    }

    public ImageDto getImage(String key) {
        try {
            ResponseInputStream<GetObjectResponse> object = s3Client.getObject(
                    GetObjectRequest.builder().bucket(bucketName).key(key).build()
            );

            byte[] bytes = object.readAllBytes();
            String contentType = object.response().contentType();
            String originalFilename = object.response().metadata().get("original-filename");
            return new ImageDto(bytes, contentType, originalFilename);
        } catch (Exception e) {
            log.warn("Could not read image from storage");
            return null;
        }
    }
}
