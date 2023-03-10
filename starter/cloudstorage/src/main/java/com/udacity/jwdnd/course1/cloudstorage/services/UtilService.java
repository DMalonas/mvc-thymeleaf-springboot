package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UtilService {
    private UserMapper userMapper;
    private FileMapper fileMapper;

    private NoteMapper noteMapper;

    public UtilService(UserMapper userMapper, FileMapper fileMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
        this.noteMapper = noteMapper;
    }
    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userMapper.getUser(userName);
        Integer userId = user.getUserId();
        return userId;
    }

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void updateModel(Model model) {
        model.addAttribute("newNote", new NoteForm());
        Integer userId = getUserId();
        model.addAttribute("files", fileMapper.getFilesByUserId(userId).toArray(new String[0]));
        model.addAttribute("notes", noteMapper.getNotesForUser(userId));
    }
}
