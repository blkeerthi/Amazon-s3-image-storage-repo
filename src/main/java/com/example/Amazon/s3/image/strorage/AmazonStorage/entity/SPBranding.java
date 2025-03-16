package com.example.Amazon.s3.image.strorage.AmazonStorage.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SP_Branding")
public class SPBranding implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "SP_DISPLAY_NAME")
    private String spDisplayName;

    // One-to-Many Relationship: One SP_Branding can have multiple branding configurations
    @OneToMany(mappedBy = "spBranding", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpBrandingInfo> brandingInfoList = new ArrayList<>();

    @Transient
    private List<FileInfo> filesInfo;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpDisplayName() {
        return spDisplayName;
    }

    public void setSpDisplayName(String spDisplayName) {
        this.spDisplayName = spDisplayName;
    }

    public List<SpBrandingInfo> getBrandingInfoList() {
        return brandingInfoList;
    }

    public void setBrandingInfoList(List<SpBrandingInfo> brandingInfoList) {
        this.brandingInfoList = brandingInfoList;
    }

    public List<FileInfo> getFilesInfo() {
        return filesInfo;
    }

    public void setFilesInfo(List<FileInfo> filesInfo) {
        this.filesInfo = filesInfo;
    }
}
