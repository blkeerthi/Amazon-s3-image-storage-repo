package com.example.Amazon.s3.image.strorage.AmazonStorage.repository;


import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SPBranding;
import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SpBrandingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpBrandingInfoRepository extends JpaRepository<SpBrandingInfo,Long> {
    List<SpBrandingInfo> findBySpBrandingId(Long spBrandingId);
}
