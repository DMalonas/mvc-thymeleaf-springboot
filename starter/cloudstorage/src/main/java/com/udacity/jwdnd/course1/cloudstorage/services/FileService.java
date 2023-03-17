package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.controller.enums.View;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
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


    public void addFile(MultipartFile multipartFile, Model model) throws IOException {
        Integer userId = utilService.getUserId();
        String fileName = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty()) {
            model.addAttribute("message", "No file selected");
            utilService.updateModel(model, false);
        } else if (!fileIsDuplicate(userId, fileName)) {
            try {
                byte[] fileData = multipartFile.getBytes();
                String contentType = multipartFile.getContentType();
                String fileSize = String.valueOf(multipartFile.getSize());
                File file = new File(fileName, contentType, fileSize, (long)userId, fileData);
                fileMapper.insertFile(file);
                model.addAttribute("message", "File successfully saved. ");
                utilService.updateModel(model, true);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Exception occurred while trying to save file.");
                utilService.updateModel(model, false);
            } catch (MaxUploadSizeExceededException e) {
                e.printStackTrace();
                model.addAttribute("message", "File size limit exceeded.");
                utilService.updateModel(model, false);
            }
        } else {
            model.addAttribute("message", "You tried to add a duplicate file");
            utilService.updateModel(model, false);
        }
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
        fileMapper.deleteEntrySafely(fileName, model, this.utilService);
    }
}


