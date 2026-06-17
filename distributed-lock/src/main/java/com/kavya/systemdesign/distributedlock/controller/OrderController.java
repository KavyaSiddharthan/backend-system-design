package com.kavya.systemdesign.distributedlock.controller;
import com.kavya.systemdesign.distributedlock.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final InventoryService inventoryService;

    @PostMapping("/purchase/{productId}")
    public ResponseEntity<String> purchase(@PathVariable String productId, @RequestParam(defaultValue = "1") int quantity) {
        String result = inventoryService.purchaseItem(productId, quantity);
        if (result.contains("Successful")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(409).body(result);
    }
}
