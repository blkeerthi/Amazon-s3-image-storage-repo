package com.example.Amazon.s3.image.strorage.AmazonStorage.amazonS3controller;

import com.example.Amazon.s3.image.strorage.AmazonStorage.AmazonS3Service.AmzS3StorageService;
import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SPBranding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class StorageController {
    @Autowired
    private AmzS3StorageService service;

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("spBrandingList") String spBrandingJson, @RequestParam(value="file") List<MultipartFile> file) throws FileNotFoundException, JsonProcessingException {
        // Convert JSON string to Java Object
        ObjectMapper objectMapper = new ObjectMapper();
        SPBranding spBranding = objectMapper.readValue(spBrandingJson, SPBranding.class);
        service.uploadImage(file,spBranding);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/config/{name}")
    public ResponseEntity<Map<String, Object>> getBrandingConfig(@PathVariable("name") String brandingName) {
        Map<String, Object> response = service.getBrandingConfiguration(brandingName);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Branding not found"));
        }
        return ResponseEntity.ok(response);
    }
}
