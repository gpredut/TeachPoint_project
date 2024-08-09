package com.gabrielapredut.teachpoint.backend.repository;

import com.gabrielapredut.teachpoint.backend.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    
}
