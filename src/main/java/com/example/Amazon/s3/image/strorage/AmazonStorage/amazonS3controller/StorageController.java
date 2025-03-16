package com.example.Amazon.s3.image.strorage.AmazonStorage.amazonS3controller;

import com.example.Amazon.s3.image.strorage.AmazonStorage.AmazonS3Service.AmzS3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/file")
public class StorageController {
    @Autowired
    private AmzS3StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value="file") MultipartFile file) throws FileNotFoundException {
        return new ResponseEntity<>(service.uploadImage(file), HttpStatus.OK);
    }
}
