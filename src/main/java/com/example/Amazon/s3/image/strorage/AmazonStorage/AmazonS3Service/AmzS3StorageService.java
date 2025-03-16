package com.example.Amazon.s3.image.strorage.AmazonStorage.AmazonS3Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.FileInfo;
import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SPBranding;
import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SpBrandingInfo;
import com.example.Amazon.s3.image.strorage.AmazonStorage.repository.SpBrandingInfoRepository;
import com.example.Amazon.s3.image.strorage.AmazonStorage.repository.SpBrandingRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AmzS3StorageService {

    private static final Logger log = LoggerFactory.getLogger(AmzS3StorageService.class);

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private SpBrandingRepository spBrandingRepository;

    @Autowired
    private SpBrandingInfoRepository spBrandingInfoRepository;

    @Autowired
    private AmazonS3 s3;

    public void uploadImage(List<MultipartFile> files, SPBranding spBranding) throws FileNotFoundException {
        List<SpBrandingInfo> brandingUrlsList = new ArrayList<>();
        Map<String, MultipartFile> fileMap = files.stream()
                .collect(Collectors.toMap(MultipartFile::getOriginalFilename, file -> file));

        Map<String,MultipartFile> matchedFiles = getFilesForBranding(fileMap, spBranding);

        if (matchedFiles.isEmpty()) {
            throw new RuntimeException("No valid branding files found. Upload at least one.");
        }
        for (Map.Entry<String, MultipartFile> entry : matchedFiles.entrySet()) {
            MultipartFile file = entry.getValue();
            File fileonj = convertMultipartFile(file);
            String filename= file.getOriginalFilename();
            s3.putObject(new PutObjectRequest(bucketName, filename, fileonj));
            String imageUrl = s3.getUrl(bucketName,filename).toString();
            SpBrandingInfo spBrandingInfo = new SpBrandingInfo();
            spBrandingInfo.setName(entry.getKey());
            spBrandingInfo.setUrl(imageUrl);
            spBrandingInfo.setSpBranding(spBranding);  // Associate with SPBranding
            brandingUrlsList.add(spBrandingInfo);
            fileonj.delete();
        }
        spBranding.getBrandingInfoList().addAll(brandingUrlsList);
        spBrandingRepository.save(spBranding);
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
    private Map<String,MultipartFile> getFilesForBranding(Map<String, MultipartFile> fileMap, SPBranding spBranding) {

        Map<String,MultipartFile> matchedFiles = new HashMap<>();
        if (spBranding.getFilesInfo()!= null) {
            for (FileInfo fileInfo : spBranding.getFilesInfo()) {
                String fileName = fileInfo.getFileName();

                if (fileName != null) {
                    switch (fileInfo.getName()) {
                        case "ImageLogo":
                            matchedFiles.put("ImageLogo",fileMap.get(fileName));
                            break;
                        case "BackgroundImage":
                            matchedFiles.put("BackgroundImage",fileMap.get(fileName));
                            break;
                        case "UserIcon":
                            matchedFiles.put("UserIcon",fileMap.get(fileName));
                            break;
                        default:
                            System.out.println("Unknown file type: " + fileInfo.getName());
                            break;
                    }
                } else {
                    System.out.println("No matching file found for: " + fileInfo.getName());
                }
            }
        }
        return matchedFiles;
    }
    public Map<String, Object> getBrandingConfiguration(String brandingName) {
        SPBranding spBranding = spBrandingRepository.findByName(brandingName);
        if (spBranding == null) {
            return Collections.emptyMap();  // Return empty response if branding not found
        }

        List<SpBrandingInfo> brandingInfos = spBrandingInfoRepository.findBySpBrandingId(spBranding.getId());

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("spDisplayName", spBranding.getSpDisplayName());

        List<Map<String, String>> filesInfo = brandingInfos.stream()
                .map(info -> Map.of(
                        "name", info.getName(),
                        "fileName", info.getUrl()))
                .collect(Collectors.toList());

        response.put("filesInfo", filesInfo);
        return response;
    }

    public SPBranding getDefaultBrandingConfiguration() {
        SPBranding defaultBranding = new SPBranding();
        defaultBranding.setSpDisplayName("Default Branding");
        return defaultBranding;
    }

}
