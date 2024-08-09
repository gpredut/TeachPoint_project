package com.gabrielapredut.teachpoint.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielapredut.teachpoint.backend.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{

}
