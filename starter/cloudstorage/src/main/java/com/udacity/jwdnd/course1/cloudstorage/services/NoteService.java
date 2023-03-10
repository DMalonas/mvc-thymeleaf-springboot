package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private UtilService utilServive;

    public NoteService(NoteMapper noteMapper, UtilService utilServive) {
        this.noteMapper = noteMapper;
        this.utilServive = utilServive;
    }

    public void updateNote(String noteId, String title, String description) {
        noteMapper.updateNote(getNote(Integer.parseInt(noteId)).getNoteId(), title, description);
    }
    public void addNote(String title, String description) {
        noteMapper.insert(new Note(title, description, utilServive.getUserId()));
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    private Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
}
