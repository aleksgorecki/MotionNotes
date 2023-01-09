package com.example.motionnotes;

public class Event {
    private int id;
    private String name;
    private String date;
    private String hour;
    private String content;
    private int position;

    public Event(int id, String name, String date, String hour, String content, int position) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.content = content;
        this.position = position;
    }

    public Event() {
        id=-1;
        name="";
        date="00/00/0000";
        hour="00:00";
        content="";
        position=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", content='" + content + '\'' +
                ", position=" + position +
                '}';
    }
}
