package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    FileMapper fileMapper;
    UserMapper userMapper;

    private UtilService utilService;

    public HomeController(FileMapper fileMapper, UserMapper userMapper, UtilService utilService) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
        this.utilService = utilService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model) {
        try {
            boolean notLoggedInYet = utilService.getUserId() == -1;
            if (notLoggedInYet) {
                return "login";
            }
            utilService.updateModel(model, true);
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }



    @PostMapping("/home")
    public String addMessage(@ModelAttribute("newCredential") CredentialsForm newCredential) {
        return "home";
    }

}
