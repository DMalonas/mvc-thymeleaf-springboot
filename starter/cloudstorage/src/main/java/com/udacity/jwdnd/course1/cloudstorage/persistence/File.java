package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private Long fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private Long userId;
    private byte[] fileData;

    public File(String fileName, String contentType, String fileSize, long userId, byte[] fileData) {
        fileName = fileName;
        contentType = contentType;
        fileSize = fileSize;
        userId = userId;
        fileData = fileData;
    }
}

