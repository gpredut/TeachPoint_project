package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.Course;
import com.gabrielapredut.teachpoint.backend.model.Instructor;
import com.gabrielapredut.teachpoint.backend.repository.CourseRepository;
import com.gabrielapredut.teachpoint.backend.repository.InstructorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Transactional(readOnly = true)
    public List<Course> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            if (course.getInstructor() != null) {
                logger.info("Instructor ID: {}", course.getInstructor().getId());
            }
            logger.info("Number of lectures: {}", course.getLectures().size());
        }
        return courses;
    }

    @Transactional(readOnly = true)
    public Optional<Course> findCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        course.ifPresent(c -> {
            if (c.getInstructor() != null) {
                logger.info("Instructor ID: {}", c.getInstructor().getId());
            }
            logger.info("Number of lectures: {}", c.getLectures().size());
        });
        return course;
    }

    @Transactional
    public Course saveCourse(Course course) {
        logger.info("Saving course: {}", course.getTitle());

        // Validate course title
        if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Course title must be provided.");
        }

        // Validate and set the instructor
        if (course.getInstructor() == null || course.getInstructor().getId() <= 0) {
            throw new IllegalArgumentException("Instructor ID must be provided.");
        }

        Instructor instructor = instructorRepository.findById(course.getInstructor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Instructor with ID " + course.getInstructor().getId() + " not found"));

        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course with ID " + id + " does not exist.");
        }
        courseRepository.deleteById(id);
    }
}

