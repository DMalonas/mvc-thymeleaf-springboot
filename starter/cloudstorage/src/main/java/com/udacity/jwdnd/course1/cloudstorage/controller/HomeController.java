package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    FileMapper fileMapper;
    UserMapper userMapper;

    private UtilService utilService;

    public HomeController(FileMapper fileMapper, UserMapper userMapper, UtilService utilService) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
        this.utilService = utilService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        try {
            utilService.updateModel(model);
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }



    @PostMapping()
    public String addMessage() {
        return "home";
    }

}
