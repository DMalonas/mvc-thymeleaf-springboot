package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                   Model model) throws IOException {
        fileService.addFile(SecurityContextHolder.getContext().getAuthentication(), file, model);
        return "result";
    }

    @GetMapping(value = "/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileMapper.getFileByName(fileName).getFileData();
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


//    @PostMapping
//    public String newFile(
//            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
//            Model model) throws IOException {
//        String userName = authentication.getName();
//        User user = userService.getUser(userName);
//        Integer userId = user.getUserId();
//        List<File> fileListings = fileService.getFileListings(userId);
//        MultipartFile multipartFile = newFile.getFile();
//        String fileName = multipartFile.getOriginalFilename();
//        boolean fileIsDuplicate = false;
//        for (String fileListing: fileListings) {
//            if (fileListing.equals(fileName)) {
//                fileIsDuplicate = true;
//                break;
//            }
//        }
//        if (!fileIsDuplicate) {
//            fileService.addFile(multipartFile);
//            model.addAttribute("result", "success");
//        } else {
//            model.addAttribute("result", "error");
//            model.addAttribute("message", "You have tried to add a duplicate file.");
//        }
//        model.addAttribute("files", fileService.getFileListings(userId));
//        return "result";
//    }
    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile(
            Authentication authentication, @PathVariable String fileName,
            Model model) {
//        fileService.deleteFile(fileName);
//        Integer userId = getUserId(authentication);
//        model.addAttribute("files", fileService.getFileListings(userId));
//        model.addAttribute("result", "success");

        return "result";
}

}
