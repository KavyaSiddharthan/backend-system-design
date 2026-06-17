package com.kavya.systemdesign.featureflags.controller;
import com.kavya.systemdesign.featureflags.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/features")
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureFlagService featureFlagService;

    @PostMapping("/{featureName}")
    public ResponseEntity<String> toggleFeature(@PathVariable String featureName, @RequestParam boolean enable) {
        featureFlagService.setFeatureFlag(featureName, enable);
        return ResponseEntity.ok("Feature " + featureName + " set to: " + enable);
    }

    @GetMapping("/checkout")
    public ResponseEntity<String> checkout() {
        if (featureFlagService.isFeatureEnabled("new_checkout_flow")) {
            return ResponseEntity.ok("Executing NEW checkout flow logic...");
        } else {
            return ResponseEntity.ok("Executing LEGACY checkout flow logic...");
        }
    }
}
