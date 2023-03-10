package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    private final UtilService utilService;
    private FileService fileService;

    @Autowired
    public FileController(FileMapper fileMapper, UserService userService, UtilService utilService, FileService fileService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.utilService = utilService;
        this.fileService = fileService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                   Model model) throws IOException {
        fileService.addFile(file, model);
        utilService.updateModel(model);
        return "home";
    }

    @GetMapping(value = "/{fileName}",
            produces = {MediaType.APPLICATION_PDF_VALUE,
                    MediaType.TEXT_PLAIN_VALUE,
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_GIF_VALUE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileMapper.getFileByName(fileName).getFileData();
    }


    @GetMapping(value = "/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName, Model model) {
        fileService.deleteFile(fileName, model);
        utilService.updateModel(model);
        return "home";
    }

}
