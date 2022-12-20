package com.example.motionnotes;

public class Note {

    private int id;
    private String content;
    private int position;

    public Note(int id, String content, int position) {
        this.id = id;
        this.content = content;
        this.position = position;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", position=" + position +
                '}';
    }
}
