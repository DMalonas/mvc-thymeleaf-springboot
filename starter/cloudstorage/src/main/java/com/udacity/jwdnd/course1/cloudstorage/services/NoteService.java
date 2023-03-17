package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private UtilService utilServive;

    public NoteService(NoteMapper noteMapper, UtilService utilServive) {
        this.noteMapper = noteMapper;
        this.utilServive = utilServive;
    }

    public void updateNote(String noteId, String title, String description, Model model) {
        if (noteMapper.updateNote(getNote(Integer.parseInt(noteId)).getNoteId(), title, description) > 0) {
            model.addAttribute("message", "Note updated");
            utilServive.updateModel(model, true);
        } else {
            model.addAttribute("message", "Could not update note, try again later");
            utilServive.updateModel(model, false);
        }
    }
    public void addNote(String title, String description, Model model) {
        if (noteMapper.insert(new Note(title, description, utilServive.getUserId())) > 0) {
            model.addAttribute("message", "Note added");
            utilServive.updateModel(model, true);
        } else {
            model.addAttribute("message", "Could not add note, try again later");
            utilServive.updateModel(model, false);
        }
    }

    public void deleteNote(Integer noteId, Model model) {
        if (noteMapper.deleteNote(noteId) > 0) {
            model.addAttribute("message", "Note deleted");
            utilServive.updateModel(model, true);
        } else {
            model.addAttribute("message", "Could not delete note, try again later");
            utilServive.updateModel(model, false);
        }
    }

    private Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
}
