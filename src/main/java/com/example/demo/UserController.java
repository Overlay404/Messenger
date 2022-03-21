package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
public class UserController {

    ArrayList<Message> messagesList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    ArrayList<Group> groupsList = new ArrayList<>();
    Boolean secureUser = false;

    //Groups
    @RequestMapping("/addGroups")
    public boolean addGroups(@RequestParam ArrayList usersCodeList, @RequestParam String name){
        Group group = new Group();
        group.setId(groupsList.size() + 1);
        group.setName(name);
        group.setUsersCodeList(usersCodeList);
        groupsList.add(group);
        return true;
    }
    @RequestMapping("/showGroups")
    public ArrayList<Group> showGroups(@RequestParam String code){
        ArrayList<Group> groupsWithThisUser = new ArrayList<>();
        for(int i = 0;i < groupsList.size(); i++){
            ArrayList validUserList = groupsList.get(i).getUsersCodeList();
            for(int j = 0; j < validUserList.size(); j++){
                if(validUserList.get(j).equals(code)){
                    groupsWithThisUser.add(groupsList.get(i));
                }
            }
        }
        return groupsWithThisUser;
    }
    @RequestMapping("/showAllGroups")
    public ArrayList<Group> showAllGroups(){
        return groupsList;
    }

    //User
    @RequestMapping("/addUser")
    public boolean addUser(@RequestParam String code, @RequestParam String name){
        User user = new User();
        user.setId(usersList.size() + 1);
        user.setCode(code);
        user.setName(name);
        usersList.add(user);
        return true;
    }
    @RequestMapping("/showUser")
    public ArrayList<User> showUser(@RequestParam String code){
        ArrayList<User> showUserWithTheCode = new ArrayList<>();
        for(int i = 0; i < usersList.size();i++){
            if(usersList.get(i).code.equals(code)){
                showUserWithTheCode.add(usersList.get(i));
            }
        }
        return showUserWithTheCode;
    }
    @RequestMapping("/showAllUser")
    public ArrayList<User> showAllUser(){
        return usersList;
    }

    //message
    @RequestMapping("/addMessage")
    public boolean addMessage(@RequestParam  String text, @RequestParam int idGroups, @RequestParam String nameUser){
        Message message = new Message();
        message.text = text;
        message.idGroup = idGroups;
        message.name = nameUser;
        messagesList.add(message);
        return true;
    }
    @RequestMapping("/showMessage")
    public ArrayList<Message> showMessage(@RequestParam int idGroups){
        return messagesList.stream().filter(message -> message.idGroup == idGroups).collect(Collectors.toCollection(ArrayList::new));
    }
    //verification
    @RequestMapping("/verification")
    public boolean verification(@RequestParam String codeUser) {
        if (usersList != null) {
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).code.equals(codeUser)) {
                    secureUser = true;
                } else {
                    secureUser = false;
                }
            }
        } else {
            secureUser = false;
        }
        return secureUser;
    }
}
