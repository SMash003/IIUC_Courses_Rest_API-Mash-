package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.controller;

import java.util.List;

public class AssignTeachersRequest {
    private List<String> teachers;
    private String semester;

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}

