package com.amarpreetsinghprojects.educonnect;

/**
 * Created by kulvi on 07/27/17.
 */

public class Student {

    String username,email,userID,college,course;

    public Student() {
    }

    public Student(String username, String email, String userID, String college, String course) {
        this.username = username;
        this.email = email;
        this.userID = userID;
        this.college = college;
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
