package com.gabrielapredut.teachpoint.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielapredut.teachpoint.backend.model.User;

@RestController
public class YourController {

    private static final Logger logger = LoggerFactory.getLogger(YourController.class);

    @PostMapping("/api/data")
    public ResponseEntity<String> yourEndpoint(@RequestBody User data) {
        try {
            // Log the received data
            logger.info("Received data: {}", data);

            // Validate the received data
            if (data == null) {
                logger.error("Received null data");
                return ResponseEntity.badRequest().body("No data received");
            }

            // Example of additional validation
            if (data.getUsername() == null || data.getUsername().isEmpty()) {
                logger.error("Invalid data: Username is missing");
                return ResponseEntity.badRequest().body("Username is required");
            }

            // Process the data (e.g., save to the database or perform other operations)
            // ...

            // Successful processing
            return ResponseEntity.ok("Data processed successfully");
        } catch (Exception e) {
            // Log the exception and return an error response
            logger.error("Error processing data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing data");
        }
    }
}
