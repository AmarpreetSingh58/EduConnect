package com.amarpreetsinghprojects.educonnect.Teacher;

/**
 * Created by kulvi on 07/27/17.
 */

public class Teacher {

    String username,email,uid;

    public Teacher() {
    }

    public Teacher(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
