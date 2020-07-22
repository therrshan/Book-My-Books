package com.example.myapplication;

public class CurrentUser {
    public static String firembaseUser;
    public static String  getFirembaseUser() {
        return firembaseUser;
    }

    public static void setFirembaseUser(String firembaseUser) {
        CurrentUser.firembaseUser = firembaseUser;
    }



}
