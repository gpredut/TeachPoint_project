package com.gabrielapredut.teachpoint.backend.repository;

import com.gabrielapredut.teachpoint.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
