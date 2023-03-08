package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }


    public void addFile(MultipartFile multipartFile, String userName) throws IOException {
        try {
            byte[] fileData = Files.readAllBytes(Paths.get(multipartFile.getOriginalFilename()));
            // Use fileData as needed
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        User user = userMapper.getUser(userName);
        Integer userId = user.getUserId();
        File file = new File(fileName, contentType, fileSize, (long)userId, fileData);
        fileMapper.insertFile(file);
    }




}


