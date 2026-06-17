package com.kavya.systemdesign.distributedlock.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final RedissonClient redissonClient;
    
    // Simulating database inventory
    private int stock = 10;

    public String purchaseItem(String productId, int quantity) {
        String lockKey = "lock:inventory:" + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // Wait up to 5 seconds to acquire the lock. Automatically unlock after 10 seconds.
            boolean isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            
            if (isLocked) {
                log.info("Lock acquired by thread {} for product {}", Thread.currentThread().getId(), productId);
                
                if (stock >= quantity) {
                    // Simulate DB latency
                    Thread.sleep(500);
                    stock -= quantity;
                    log.info("Successfully purchased. Remaining stock: {}", stock);
                    return "Purchase Successful! Remaining stock: " + stock;
                } else {
                    log.warn("Out of stock!");
                    return "Purchase Failed: Out of Stock. Remaining: " + stock;
                }
            } else {
                log.error("Could not acquire lock for product {}", productId);
                return "Purchase Failed: System busy. Please try again.";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Purchase Failed: Interrupted";
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("Lock released by thread {}", Thread.currentThread().getId());
            }
        }
    }
}
