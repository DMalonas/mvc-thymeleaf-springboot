package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    private final UtilService utilService;


    public FileService(FileMapper fileMapper, UserMapper userMapper, UtilService utilService) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
        this.utilService = utilService;
    }


    public int addFile(MultipartFile multipartFile, Model model) throws IOException {
        Integer userId = utilService.getUserId();
        String fileName = multipartFile.getOriginalFilename();
        if (!fileIsDuplicate(userId, fileName)) {
            try {
                byte[] fileData = multipartFile.getBytes();
                String contentType = multipartFile.getContentType();
                String fileSize = String.valueOf(multipartFile.getSize());
                File file = new File(fileName, contentType, fileSize, (long)userId, fileData);
                int id = fileMapper.insertFile(file);
                utilService.updateModel(model);
                return id;
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Exception occurred while trying to save file.");
                utilService.updateModel(model);
                return -1;
            }
        }
        model.addAttribute("error", "You have tried to add a duplicate file.");
        utilService.updateModel(model);
        return -1;
    }

    private boolean fileIsDuplicate(Integer userId, String fileName) {
        List<String> fileListings = getFilesByUserId(userId);
        for (String fileListing: fileListings) {
            if (fileListing.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public void deleteFile(String fileName, Model model) {
        Integer userId = utilService.getUserId();
        fileMapper.deleteEntrySafely(fileName);
        utilService.updateModel(model);
    }
}


