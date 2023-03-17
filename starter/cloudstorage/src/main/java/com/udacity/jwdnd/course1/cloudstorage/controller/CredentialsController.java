package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import com.udacity.jwdnd.course1.cloudstorage.persistence.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private CredentialsService credentialsService;
    private UtilService utilService;

    public CredentialsController(CredentialsService credentialsService,
                                 UtilService utilService) {
        this.credentialsService = credentialsService;
        this.utilService = utilService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newCredential") CredentialsForm newCredential, Model model, @ModelAttribute("credential") CredentialsForm credentials) {
        utilService.updateModel(model);
        return "home";
    }


    @PostMapping
    public String updateCredentials(@ModelAttribute("newCredential") CredentialsForm newCredential,@ModelAttribute("newCredential") CredentialsForm credentials, Model model) {
        if (credentials.getCredentialId().isBlank()) {
            credentialsService.addCredentials(credentials.getUrl(), credentials.getUserName(),
                    credentials.getPassword());
        } else {
            Credential savedCredential = credentialsService.getCredential(Integer.parseInt(credentials.getCredentialId()));
            credentialsService.updateCredential(savedCredential.getCredentialId(), credentials.getUserName(), credentials.getUrl(),
                    credentials.getPassword());
        }
        utilService.updateModel(model);
        model.addAttribute("credentials", credentialsService.getCredentials(utilService.getUserId()));
        return "home";
    }

    @GetMapping(value = "/delete/{credentialId}")
    public String deleteCredentials(@ModelAttribute("newCredential") CredentialsForm newCredential,@PathVariable Integer credentialId, Model model) {
        credentialsService.deleteCredential(credentialId);
        utilService.updateModel(model);
        return "home";
    }
}
