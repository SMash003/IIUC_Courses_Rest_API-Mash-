package com.iiuc.cse.b58.s3f.g5.iiuc_course_api.service;

import java.util.List;
import java.util.Optional;

import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.model.Course;
import com.iiuc.cse.b58.s3f.g5.iiuc_course_api.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private Long nextId = 100L;

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

        if (!course.getCourseCode().matches("[A-Z]{3,4}\\d{4}")) {
            throw new IllegalArgumentException(
                    "Invalid course code format.Course code must be 3–4 uppercase letters followed by 4 digits.");
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

    public List<Course> getCourseByDepartment(Long deptId) {
        return courseRepository.findByDepartmentId(deptId);
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
        existingCourse.setCourseTeachers(updatedCourse.getCourseTeachers());

        return courseRepository.updateCourse(existingCourse);
    }

    public boolean deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getCourseTeachers() != null && !course.getCourseTeachers().isEmpty()) {
            throw new IllegalStateException("Cannot delete course with assigned faculty");
        }

        return courseRepository.deleteById(id);
    }

    public boolean deleteCourseByCode(String code) {
        courseRepository.findByCourseCode(code).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return courseRepository.deleteByCourseCode(code);
    }

    public Course assignTeachersToCourse(Long courseId, List<String> teachers, String semester) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Validate each teacher: max 3 courses per semester
        for (String teacher : teachers) {
            long assignedCount = courseRepository.findAll().stream()
                    .filter(c -> semester.equals(c.getSemester()) &&
                            c.getCourseTeachers().contains(teacher))
                    .count();

            if (assignedCount >= 3) {
                throw new IllegalStateException(
                        "Teacher " + teacher + " already has 3 courses in " + semester);
            }

            // Add teacher to course if not already added
            course.addCourseTeacher(teacher);
        }

        course.setSemester(semester);
        return courseRepository.updateCourse(course);
    }
}
