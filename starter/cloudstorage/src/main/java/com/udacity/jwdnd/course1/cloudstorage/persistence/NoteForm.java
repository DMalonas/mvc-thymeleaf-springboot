package com.udacity.jwdnd.course1.cloudstorage.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteForm {
    private String noteId;
    private String title;
    private String description;
}
