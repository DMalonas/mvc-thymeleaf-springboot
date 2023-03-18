package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.controller.enums.View;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String loginView(Model model, RedirectAttributes redirectAttributes) {
        if (redirectAttributes.containsAttribute("signupSuccess")) {
            model.addAttribute("signupSuccess", (boolean) redirectAttributes.getAttribute("signupSuccess"));
        }
        if (redirectAttributes.containsAttribute("signupError")) {
            model.addAttribute("signupError", (String) redirectAttributes.getAttribute("signupError"));
        }
        return View.LOGIN.getText();
    }
}
