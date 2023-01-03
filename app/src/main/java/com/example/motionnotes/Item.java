package com.example.motionnotes;

public class Item {
    private int id;
    private String content;
    private boolean isDone;
    private int position;
    private int list_id;

    public Item(int id, String content, boolean isDone, int position, int list_id) {
        this.id = id;
        this.content = content;
        this.isDone = isDone;
        this.position = position;
        this.list_id = list_id;
    }

    public Item() {
        id = 0;
        content = "";
        isDone = false;
        position = 0;
        list_id = -1;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isDone=" + isDone +
                ", position=" + position +
                ", list_id=" + list_id +
                '}';
    }
}
