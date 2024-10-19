package com.example.Neighborhood_Walk.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.url}")  // 你的 MinIO URL，例如 http://localhost:9000
    private String minioUrl;
    public MinioService(@Value("${minio.url}") String url,
                        @Value("${minio.access-key}") String accessKey,
                        @Value("${minio.secret-key}") String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String uploadFile(MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // 上传文件到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // 返回文件的访问 URL
            return minioUrl + "/" + bucketName + "/" + fileName;

        } catch (MinioException e) {
            throw new RuntimeException("Error while uploading file to MinIO: " + e.getMessage());
        }
    }
}

