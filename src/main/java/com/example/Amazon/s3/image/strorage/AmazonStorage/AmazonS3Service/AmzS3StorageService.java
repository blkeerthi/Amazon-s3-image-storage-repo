package com.example.Amazon.s3.image.strorage.AmazonStorage.AmazonS3Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class AmzS3StorageService {

    private static final Logger log = LoggerFactory.getLogger(AmzS3StorageService.class);

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3;

    public String uploadImage(MultipartFile multipartFile) throws FileNotFoundException {
        File fileonj = convertMultipartFile(multipartFile);
        String filename= System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
        s3.putObject(new PutObjectRequest(bucketName,filename,fileonj));
        fileonj.delete();
        return "File Uploaded " + filename;
    }

    private File convertMultipartFile(MultipartFile file) throws FileNotFoundException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile{}", String.valueOf(e));
            throw new RuntimeException(e);
        }
        return convertedFile;
    }
}
