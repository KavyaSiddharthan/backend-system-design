package com.kavya.systemdesign.urlshortener.service;
import com.kavya.systemdesign.urlshortener.model.Url;
import com.kavya.systemdesign.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository repository;

    public String shortenUrl(String originalUrl) {
        // Simple generation for demo. In production, use Base62 encoding on an auto-incrementing ID or ZooKeeper/Snowflake
        String shortCode = UUID.randomUUID().toString().substring(0, 7);
        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .clickCount(0)
                .build();
        repository.save(url);
        return shortCode;
    }

    @Cacheable(value = "urls", key = "#shortCode")
    public String getOriginalUrl(String shortCode) {
        Url url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        // Async update click count in production
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);
        return url.getOriginalUrl();
    }
}
