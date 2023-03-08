package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileMapper fileMapper;

    private UserService userService;

    private FileService fileService;

    @Autowired
    public FileController(FileMapper fileMapper, UserService userService, FileService fileService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile file,
                                   Model model) throws IOException {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        fileService.addFile(file, userName);
        return "home";
    }


//    @PostMapping("/upload")
//    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile file,
//                                   Model model) throws IOException {
//        String userName = authentication.getName();
//        fileService.addFile(file, userName);
//        model.addAttribute("result", "success");
////        } else {
////            model.addAttribute("result", "error");
////            model.addAttribute("message", "You have tried to add a duplicate file.");
////        }
////        model.addAttribute("files", fileService.getFileListings(userId));
//
//        return "home";
//    }
}
