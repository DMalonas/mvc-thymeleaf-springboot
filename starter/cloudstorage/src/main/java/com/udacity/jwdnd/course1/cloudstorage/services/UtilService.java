package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Credential;
import com.udacity.jwdnd.course1.cloudstorage.persistence.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.persistence.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class UtilService {
    private UserMapper userMapper;
    private FileMapper fileMapper;

    private NoteMapper noteMapper;

    private CredentialsMapper credentialsMapper;;
    private EncryptionService encryptionService;


    public UtilService(UserMapper userMapper, FileMapper fileMapper, NoteMapper noteMapper, CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }
    public Integer getUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userMapper.getUser(userName);
            Integer userId = user.getUserId();
            return userId;
        } catch(Exception e) {
            return -1;
        }
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

     public void updateModel(Model model, boolean operationResult) {
        if (operationResult) {
            model.addAttribute("result","success");
        } else {
            model.addAttribute("result","error");
        }
        Integer userId = getUserId();
        model.addAttribute("newNote", new NoteForm());
        model.addAttribute("files", fileMapper.getFilesByUserId(userId).isEmpty() ? new String[0] : fileMapper.getFilesByUserId(userId).toArray(new String[0]));
        model.addAttribute("notes", noteMapper.getNotesByUserId(userId));
        model.addAttribute("credentials", credentialsMapper.getCredentialsByUserId(userId));
        List<Credential> credentialsByUserId = credentialsMapper.getCredentialsByUserId(userId);
        Credential dummyCredential = new Credential();
//        dummyCredential.setCredentialId(1);
//        dummyCredential.setKey("a");
//        dummyCredential.setPassword("pass");
//        dummyCredential.setUrl("http://localhost:8080");
//        dummyCredential.setUserid(1);
//        dummyCredential.setUserName("username");
        System.out.println("------------------>" + credentialsByUserId);
//        model.addAttribute("credentials", credentialsByUserId.isEmpty() ? dummyCredential : credentialsByUserId);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("newCredential", new CredentialsForm());
    }

    public List<String> getEncryptedData(String credentialsPassword) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return new ArrayList<>(Arrays.asList(encodedKey, encryptionService.encryptValue(credentialsPassword, encodedKey)));
    }
}
