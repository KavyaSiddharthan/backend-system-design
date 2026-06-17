package com.kavya.systemdesign.urlshortener.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls", indexes = {@Index(columnList = "shortCode", unique = true)})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Url {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String originalUrl;
    
    @Column(nullable = false, unique = true)
    private String shortCode;
    
    private LocalDateTime createdAt;
    private long clickCount;
}
