package com.example.myapplication;

public class User {

    private String name,password,mail_id,contact;

    public User() {
    }

    public User(String name, String password, String mail_id, String contact) {
        this.name = name;
        this.password = password;
        this.mail_id = mail_id;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String  getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
