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

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }


    public int addFile(Authentication authentication, MultipartFile multipartFile, Model model) throws IOException {
        String userName = authentication.getName();
        User user = userMapper.getUser(userName);
        Integer userId = user.getUserId();
        String fileName = multipartFile.getOriginalFilename();
        if (!fileIsDuplicate(userId, fileName)) {
            try {
                byte[] fileData = multipartFile.getBytes();
                String contentType = multipartFile.getContentType();
                String fileSize = String.valueOf(multipartFile.getSize());
                File file = new File(fileName, contentType, fileSize, (long)userId, fileData);
                int id = fileMapper.insertFile(file);
                model.addAttribute("files", fileMapper.getFiles());
                model.addAttribute("result", "success");

                return id;
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("result", "error");
                model.addAttribute("message", "Exception occurred while trying to save file.");
                return -1;
            }
        }
        model.addAttribute("result", "error");
        model.addAttribute("message", "You have tried to add a duplicate file.");
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


//    public void addFile(MultipartFile multipartFile) throws IOException {
//
//
//
//        InputStream fis = multipartFile.getInputStream();
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        int nRead;
//        byte[] data = new byte[1024];
//        while ((nRead = fis.read(data, 0, data.length)) != -1) {
//            buffer.write(data, 0, nRead);
//        }
//        buffer.flush();
//        byte[] fileData = buffer.toByteArray();
//
//        String fileName = multipartFile.getOriginalFilename();
//        String contentType = multipartFile.getContentType();
//        String fileSize = String.valueOf(multipartFile.getSize());
//        User user = userMapper.getUser(userName);
//        Integer userId = user.getUserId();
//        File file = new File(fileName, contentType, fileSize, (long)userId, fileData);
//        fileMapper.insertFile(file);
//    }

}


