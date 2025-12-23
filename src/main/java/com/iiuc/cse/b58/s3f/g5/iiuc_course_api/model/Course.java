package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> courseTeachers = new ArrayList<>();
    private String department;

    public Course() {
    }

    public Course(Long id, String courseCode, String courseTitle, Double courseCredit, String courseType,
            String semester, Long departmentId, List<String> courseTeachers) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseType = courseType;
        this.semester = semester;
        this.departmentId = departmentId;
        this.courseTeachers = courseTeachers;

        switch (departmentId.intValue()) {
            case 1:
                this.department = "CSE";
                break;
            case 2:
                this.department = "EEE";
                break;
            case 3:
                this.department = "CIVIL";
                break;
            case 4:
                this.department = "ETE";
                break;
            case 5:
                this.department = "CCE";
                break;
            case 6:
                this.department = "PHARMA";
            default:
                this.department = "Not existed";
                break;
        }
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

     public List<String> getCourseTeachers() {
        return courseTeachers;
    }

    public void setCourseTeachers(List<String> courseTeachers) {
        this.courseTeachers = courseTeachers;
    }

    public void addCourseTeacher(String teacher) {
        if (!this.courseTeachers.contains(teacher)) {
            this.courseTeachers.add(teacher);
        }
    }

    public String getDepartment() {
        if (departmentId == null)
            return "Not existed";

        switch (departmentId.intValue()) {
            case 1:
                return "CSE";
            case 2:
                return "EEE";
            case 3:
                return "CIVIL";
            case 4:
                return "ETE";
            case 5:
                return "CCE";
            case 6:
                return "PHARMA";
            default:
                return "Not existed";
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseCredit=" + courseCredit +
                ", courseType='" + courseType + '\'' +
                ", semester='" + semester + '\'' +
                ", departmentId=" + departmentId +
                ", courseTeacher=" + courseTeachers + '\'' +
                ", department=" + department + '\'' +
                '}';
    }
}