package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.repository;

import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository {

    private final List<Course> courseList = new ArrayList<>();

    public void saveCourse(Course course) {
        courseList.add(course);
    }

    public void saveCourses(List<Course> courses) {
        courseList.addAll(courses);
    }

    public List<Course> findAll() {
        return courseList;
    }

    public List<Course> findByDepartmentId(Long departmentId) {
        return courseList.stream()
                .filter(c -> departmentId.equals(c.getDepartmentId()))
                .toList();
    }

    public Optional<Course> findById(Long id) {
        return courseList.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst();
    }

    public Optional<Course> findByCourseCode(String code) {
        return courseList.stream()
                .filter(c -> code.equals(c.getCourseCode()))
                .findFirst();
    }

    public Course updateCourse(Course course) {
        findById(course.getId()).ifPresent(existing -> {
            existing.setCourseCode(course.getCourseCode());
            existing.setCourseTitle(course.getCourseTitle());
            existing.setCourseCredit(course.getCourseCredit());
            existing.setCourseType(course.getCourseType());
            existing.setSemester(course.getSemester());
            existing.setCourseTeacher(course.getCourseTeacher());
        });

        return course;
    }

    public boolean deleteById(Long id) {
        return courseList.removeIf(c -> id.equals(c.getId()));
    }

}


