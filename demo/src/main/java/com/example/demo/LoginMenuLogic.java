package com.example.demo;

import java.util.ArrayList;

public class LoginMenuLogic {
    private ArrayList<Users> users;
    private UserDatabase database;
    private Users loggedInUser;


    public LoginMenuLogic(UserDatabase database) {
        this.database = database;
        this.users = database.getObjectArray();
    }

    public boolean login(String username, String password,ArrayList<Users>users) {
        System.out.println(users);
        boolean found=isUser(username, password,users);
        if(found){
            loggedInUser = database.getUser(username,password,users);
        }
        return found;
    }

    public boolean register(String username, String password,ArrayList<Users> users) {
        //System.out.println(users);
        boolean found=isUser(username, password,users);
        if(found) {
            return false;
        }
        Users newUser = new Users(username, password);
        database.addObject(newUser);
        return true;
    }
    public boolean isUser(String username, String password, ArrayList<Users>arrayList){
        boolean found = false;
        for (Users user : arrayList) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                found = true;
                return found;
            }
        }
        return found;
    }
    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public void setUsersList(ArrayList<Users> users) {
        this.users = users;
    }
}
