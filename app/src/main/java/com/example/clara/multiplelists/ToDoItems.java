package com.example.clara.multiplelists;

import java.io.Serializable;

/**
 * Created by clara on 10-3-2017.
 * Business object for a toDoItem
 */

public class ToDoItems implements Serializable {

    private int id;
    private String itemName;
    private int listId;

    // constructors
    public ToDoItems() {
    }

    public ToDoItems(int id) {
        this.id = id;
    }

    public ToDoItems(String itemName) {
        this.itemName = itemName;
    }

    public ToDoItems(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public ToDoItems(int id, String itemName, int listId) {
        this.id =  id;
        this.itemName = itemName;
        this.listId = listId;
    }

    // setters
    public void setId(int newId) {
        id = newId;
    }

    public void setItemName(String newItemName) {
        itemName = newItemName;
    }

    public void setListId(int newListId) {
        listId = newListId;
    }

    // getters
    public long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getListId() {
        return listId;
    }
}
