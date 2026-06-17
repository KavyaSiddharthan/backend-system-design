package com.kavya.systemdesign.ratelimiter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class RateLimiterIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
        // Force test limits: 3 reqs max
        registry.add("rate-limiter.capacity", () -> "3");
        registry.add("rate-limiter.refill-tokens", () -> "1");
        registry.add("rate-limiter.refill-period-seconds", () -> "10");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRateLimiterBlocksAfterCapacity() throws Exception {
        // Free endpoint never blocks
        mockMvc.perform(get("/api/v1/free")).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/free")).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/free")).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/free")).andExpect(status().isOk());

        // Limited endpoint allows exactly 3 requests (since capacity=3)
        mockMvc.perform(get("/api/v1/limited").with(request -> { request.setRemoteAddr("127.0.0.1"); return request; })).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/limited").with(request -> { request.setRemoteAddr("127.0.0.1"); return request; })).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/limited").with(request -> { request.setRemoteAddr("127.0.0.1"); return request; })).andExpect(status().isOk());

        // 4th request must be blocked (Too Many Requests)
        mockMvc.perform(get("/api/v1/limited").with(request -> { request.setRemoteAddr("127.0.0.1"); return request; })).andExpect(status().isTooManyRequests());
    }
}
