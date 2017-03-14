package com.example.clara.multiplelists;

/**
 * Created by clara on 10-3-2017.
 * Business object for a toDoList
 */

public class ToDoLists {
    private int id;
    private String listName;

    // constructors
    public ToDoLists() {
    }

    public ToDoLists(int id) {
        this.id = id;
    }

    public ToDoLists(String listName) {
        this.listName = listName;
    }

    public ToDoLists(int id, String listName) {
        this.id = id;
        this.listName = listName;
    }

    // setters
    public void setId(int newId) {
        id = newId;
    }

    public void setListName(String newListName) {
        listName = newListName;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }
}