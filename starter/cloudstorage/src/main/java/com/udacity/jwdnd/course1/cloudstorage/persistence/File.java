package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private Long fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Long userId;
    private byte[] fileData;

    public File(String fileName, String contentType, String fileSize, long userId, byte[] fileData) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }
}

