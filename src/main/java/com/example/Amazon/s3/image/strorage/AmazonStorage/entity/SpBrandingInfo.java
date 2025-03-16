package com.example.Amazon.s3.image.strorage.AmazonStorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;


@Entity
@Data
@Table(name = "SP_BRANDING_INFO")
public class SpBrandingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "SP_Branding_ID", nullable = false) // Foreign Key
    private SPBranding spBranding;

    @Column(name = "NAME")
    private String name;

    @Column(name = "Value")
    private String value;
}
