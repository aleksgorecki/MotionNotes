package com.example.motionnotes;

import java.util.ArrayList;

public class CheckList {
    private int id;
    private String name;
    private int position;

    private ArrayList<Item> items;

    public CheckList(int id, String name, int position, ArrayList<Item> items) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.items = items;
    }

    public CheckList(int id, String name, int position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public CheckList() {
        id=-1;
        name="";
        position=0;
        items = new ArrayList<>();
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItemAt(int position) {
        items.remove(position);
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", items=" + items +
                '}';
    }
}
