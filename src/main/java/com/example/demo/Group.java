package com.example.demo;

import java.util.ArrayList;

public class Group {
    int id;
    ArrayList usersCodeList;
    String name;

    public ArrayList getUsersCodeList() {
        return usersCodeList;
    }

    public void setUsersCodeList(ArrayList usersCodeList) {
        this.usersCodeList = usersCodeList;
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
}
