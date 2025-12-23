package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.controller;

import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model.Course;
import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/iiuc/courses")
public class CourseController {

    private final CourseService courseService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CourseController(CourseService courseService) {
        // Dependency injection
        this.courseService = courseService;
    }


    // ---------------- Create Course (single or multiple) ----------------
    @PostMapping
    public Object createCourse(@RequestBody Object body) {
        try {
            if (body instanceof List<?> list) {
                // Multiple courses
                List<Course> createdCourses = new ArrayList<>();
                for (Object o : list) {
                    Course course = objectMapper.convertValue(o, Course.class);
                    createdCourses.add(courseService.createCourse(course));
                }
                return createdCourses;
            } else {
                // Single course
                Course course = objectMapper.convertValue(body, Course.class);
                return courseService.createCourse(course);
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ---------------- Get all courses ----------------
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // ---------------- Get course by ID ----------------
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new IllegalArgumentException("Course not found with ID: " + id);
        }
    }

    // ---------------- Get course by Code ----------------
    @GetMapping("/code/{code}")
    public Course getCourseByCode(@PathVariable String code) {
        Optional<Course> course = courseService.getCourseByCode(code);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new IllegalArgumentException("Course not found with code: " + code);
        }
    }

    @GetMapping("/department/{deptId}")
    public List<Course> getCourseByDepartment(@PathVariable Long deptId) {
        return courseService.getCourseByDepartment(deptId);
    }

    // ---------------- Update course ----------------
    @PutMapping("/{id}")
    public Object updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            course.setId(id); // Ensure ID is used
            return courseService.updateCourse(id, course);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return e.getMessage();
        }
    }

    // ---------------- Delete course ----------------
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id) {
        try {
            boolean deleted = courseService.deleteCourse(id);
            if (deleted) {
                return "Course with ID " + id + " deleted successfully.";
            } else {
                return "Course with ID " + id + " not found or cannot delete.";
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/code/{code}")
    public String deleteCoursebyCode(@PathVariable String code) {
        try {
            boolean deleted = courseService.deleteCourseByCode(code);
            if (deleted) {
                return "Course with Code " + code + "deleted successfully.";
            } else {
                return "Course with Code " + code + "not found or cannot  delete.";
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // ---------------- Assign faculty to course ----------------
    @PutMapping("/{id}/assign")
    public Object assignTeachers(
            @PathVariable Long id,
            @RequestBody AssignTeachersRequest request) {
        try {
            return courseService.assignTeachersToCourse(id, request.getTeachers(), request.getSemester());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return e.getMessage();
        }
    }

}
