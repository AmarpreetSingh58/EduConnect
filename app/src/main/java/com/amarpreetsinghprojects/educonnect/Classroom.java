package com.amarpreetsinghprojects.educonnect;

/**
 * Created by kulvi on 07/27/17.
 */

public class Classroom {

    String classname,classuid;

    public Classroom() {
    }

    public Classroom(String classname, String classuid) {
        this.classname = classname;
        this.classuid = classuid;
    }

    public String getClassname() {
        return classname;
    }

    public String getClassuid() {
        return classuid;
    }
}
