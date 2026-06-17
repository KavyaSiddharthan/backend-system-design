package com.kavya.systemdesign.fileprocessing.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucket;
    
    private final ConcurrentHashMap<String, String> statusMap = new ConcurrentHashMap<>();

    public String initUpload() {
        String fileId = UUID.randomUUID().toString();
        statusMap.put(fileId, "PROCESSING");
        return fileId;
    }

    @Async
    public void processAndUpload(String fileId, InputStream fileStream, long contentLength, String contentType) {
        try {
            log.info("Starting background processing for file: {}", fileId);
            // Simulate heavy processing (e.g., video compression, virus scan)
            Thread.sleep(5000); 

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileId)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(fileStream, contentLength));
            statusMap.put(fileId, "COMPLETED");
            log.info("Successfully uploaded file: {}", fileId);
        } catch (Exception e) {
            log.error("Failed to process file {}", fileId, e);
            statusMap.put(fileId, "FAILED");
        }
    }

    public String getStatus(String fileId) {
        return statusMap.getOrDefault(fileId, "NOT_FOUND");
    }
}
