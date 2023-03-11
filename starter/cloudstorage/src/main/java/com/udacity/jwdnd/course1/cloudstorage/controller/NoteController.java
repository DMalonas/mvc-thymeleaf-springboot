package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.persistence.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;

    private UtilService utilService;
    public NoteController(NoteService noteService, UtilService utilService) {
        this.noteService = noteService;
        this.utilService = utilService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newCredential") CredentialsForm newCredential,Model model) {
        utilService.updateModel(model);
        return "home";
    }



    @PostMapping
    public String addNote(@ModelAttribute("newCredential") CredentialsForm newCredential,@ModelAttribute("note") NoteForm noteForm, Model model) {
        if (noteForm.getNoteId().isBlank()) {
            noteService.addNote(noteForm.getTitle(), noteForm.getDescription());
        } else {
            noteService.updateNote(noteForm.getNoteId(), noteForm.getTitle(), noteForm.getDescription());
        }
        utilService.updateModel(model);
        return "home";
    }


    @GetMapping(value = "/delete/{noteId}")
    public String delete(@ModelAttribute("newCredential") CredentialsForm newCredential,@PathVariable String noteId,
            Model model) {
        noteService.deleteNote(Integer.parseInt(noteId));
        utilService.updateModel(model);
        return "home";
    }
}
