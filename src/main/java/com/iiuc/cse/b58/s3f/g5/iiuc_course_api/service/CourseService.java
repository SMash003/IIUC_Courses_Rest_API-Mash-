package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.service;


import java.util.List;
import java.util.Optional;

import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model.Course;
import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private Long nextId = 100L;
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public Course createCourse(Course course) {
        course.setId(nextId++);
        if (courseRepository.findByCourseCode(course.getCourseCode()).isPresent()) {
            throw new IllegalArgumentException("Course code '" + course.getCourseCode() + "' already exists!");
        }
        validateCourse(course);
        courseRepository.saveCourse(course);
        return course;
    }

    private void validateCourse(Course course) {
        if (course.getCourseCredit() < 1 || course.getCourseCredit() > 4) {
            throw new IllegalArgumentException("Course credit must be between 1 and 4");
        }
        
        if (!course.getCourseCode().matches("[A-Z]{3}\\d{3}")) {
            throw new IllegalArgumentException("Invalid course code format. Use format: CSE2321");
        }
        
        if (course.getCourseTitle() == null || course.getCourseTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be empty");
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String code) {
        return courseRepository.findByCourseCode(code);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourseOpt = courseRepository.findById(id);
        
        if (existingCourseOpt.isEmpty()) {
            throw new IllegalArgumentException("Course with ID " + id + " not found");
        }
        
        Course existingCourse = existingCourseOpt.get();
        if (!existingCourse.getCourseCode().equals(updatedCourse.getCourseCode())) {
            throw new IllegalStateException("Cannot change course code after creation");
        }

        existingCourse.setCourseTitle(updatedCourse.getCourseTitle());
        existingCourse.setCourseCredit(updatedCourse.getCourseCredit());
        existingCourse.setCourseType(updatedCourse.getCourseType());
        existingCourse.setSemester(updatedCourse.getSemester());
        existingCourse.setCourseTeacher(updatedCourse.getCourseTeacher());
        
        return courseRepository.updateCourse(existingCourse);
    }

    public boolean deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        if (course.getCourseTeacher() != null && !course.getCourseTeacher().isEmpty()) {
            throw new IllegalStateException("Cannot delete course with assigned faculty");
        }
        
        return courseRepository.deleteById(id);
    }
    public Course assignFacultyToCourse(Long courseId, String facultyName, String semester) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        long assignedCount = courseRepository.findAll().stream()
                .filter(c -> semester.equals(c.getSemester()) && 
                             facultyName.equals(c.getCourseTeacher()))
                .count();
        
        if (assignedCount >= 3) {
            throw new IllegalStateException(
                "Faculty " + facultyName + " already has 3 courses in " + semester
            );
        }
        
        course.setCourseTeacher(facultyName);
        course.setSemester(semester);
        return courseRepository.updateCourse(course);
    }
}

