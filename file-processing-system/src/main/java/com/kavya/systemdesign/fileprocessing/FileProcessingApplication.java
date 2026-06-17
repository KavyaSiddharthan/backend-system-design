package com.kavya.systemdesign.fileprocessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FileProcessingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileProcessingApplication.class, args);
    }
}
