package com.example.Amazon.s3.image.strorage.AmazonStorage.entity;

import java.io.Serializable;

public class FileInfo implements Serializable{

    private int id;

    private String fileName;
    private String name; // Extra metadata if needed

    public FileInfo() {}

    public FileInfo(String fileName, String name) {
        this.fileName = fileName;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

