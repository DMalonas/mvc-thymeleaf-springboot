package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(String title, String description, Integer userId) {
        this.noteTitle = title;
        this.noteDescription = description;
        this.userId = userId;
    }

    public Note(String title, String description) {
        this.noteTitle = title;
        this.noteDescription = description;
    }
}
