package com.kavya.systemdesign.fileprocessing.controller;
import com.kavya.systemdesign.fileprocessing.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileId = fileService.initUpload();
            // Pass the input stream directly to async service to prevent blocking the HTTP thread
            fileService.processAndUpload(fileId, file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.accepted().body("Upload initiated. Tracking ID: " + fileId);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to read file");
        }
    }

    @GetMapping("/status/{fileId}")
    public ResponseEntity<String> checkStatus(@PathVariable String fileId) {
        return ResponseEntity.ok("Status: " + fileService.getStatus(fileId));
    }
}
