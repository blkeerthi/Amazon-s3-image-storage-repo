package com.example.Amazon.s3.image.strorage.AmazonStorage.repository;


import com.example.Amazon.s3.image.strorage.AmazonStorage.entity.SPBranding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpBrandingRepository extends JpaRepository<SPBranding, Long> {
    @Query("SELECT s FROM SPBranding s WHERE s.spDisplayName = :spDisplayName")
    SPBranding findByName(@Param("spDisplayName") String spDisplayName);
}