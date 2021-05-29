package com.BrClaudProject.myprojecttry.notes;

import android.net.Uri;
import android.util.Log;

public class Note {
    private String noteText;
    private String headerNote;
    private boolean check;
    private String uploadUri;
    public Note ()
    {}

    public Note(String noteText, String headerNote) {
        this.noteText = noteText;
        this.headerNote = headerNote;
        check = false;
        uploadUri = "";

    }

    public Note(String noteText, String headerNote, String uploadUri) {
        this.noteText = noteText;
        this.headerNote = headerNote;
        this.uploadUri = uploadUri;
        check = false;


    }

    public String getUploadUri() {
        return uploadUri;
    }

    public void setUploadUri(String uploadUri) {
        this.uploadUri = uploadUri;
    }

    public String getNoteText() {
        return noteText;
    }
    public boolean isCheck() {
        return check;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getHeaderNote() {
        return headerNote;
    }

    public void setHeaderNote(String headerNote) {
        this.headerNote = headerNote;
    }
}
