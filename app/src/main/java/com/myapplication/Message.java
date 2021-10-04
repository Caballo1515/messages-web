package com.myapplication;

public class Message {
    private String name, mail, subject, message, id, time;

    public Message() {
        name = "pepe";
        mail = "pepe";
        subject = "pepe";
        message = "pepe";
        id = "pepe";
        time = "pepe";
    }

    public Message(String name, String mail, String subject, String message, String id, String time) {
        this.name = name;
        this.mail = mail;
        this.subject = subject;
        this.message = message;
        this.id = id;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
