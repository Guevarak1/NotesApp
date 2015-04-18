package com.kevguev.notesapp;

/**
 * Created by Kevin Guevara on 4/18/2015.
 */
public class Note {
    String title = null;
    String content = null;
    String time = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
