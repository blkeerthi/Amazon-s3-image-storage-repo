package com.example.Amazon.s3.image.strorage.AmazonStorage.repositorys;

import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SpBrandingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SpBrandingInfoRepository extends JpaRepository<SpBrandingInfo, Long> {
    List<SpBrandingInfo> findBySpBrandingId(Long spBrandingId);
}
