package com.gabrielapredut.teachpoint.backend.repository;

import com.gabrielapredut.teachpoint.backend.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("SELECT i FROM Instructor i LEFT JOIN FETCH i.courses WHERE i.id = :id")
    Optional<Instructor> findByIdWithCourses(@Param("id") Long id);

    @Query("SELECT i FROM Instructor i LEFT JOIN FETCH i.courses")
    List<Instructor> findAllWithCourses();
}
