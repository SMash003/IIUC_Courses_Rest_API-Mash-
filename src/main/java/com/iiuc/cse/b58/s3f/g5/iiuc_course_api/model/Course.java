package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    private Long id;
    private String courseCode;
    private String courseTitle;
    private Double courseCredit;
    private String courseType;
    private String semester;
    private Long departmentId;
    private String courseTeacher;

    public Course() {
    }

    public Course(Long id, String courseCode, String courseTitle, Double courseCredit, String courseType, 
                 String semester, Long departmentId, String courseTeacher) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.semester = semester;
        this.departmentId = departmentId;
        this.courseTeacher = courseTeacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Double getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Double courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode:'" + courseCode + '\'' +
                ", courseTitle:'" + courseTitle + '\'' +
                ", courseCredit:" + courseCredit +
                ", courseType:" + courseType + '\'' +
                ", semester:'" + semester + '\'' +
                ", departmentId=:" + departmentId + '\'' +
                ", courseTeacher='" + courseTeacher +
                '}';
    }
}