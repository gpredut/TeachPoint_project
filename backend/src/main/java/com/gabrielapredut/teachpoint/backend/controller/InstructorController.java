package com.gabrielapredut.teachpoint.backend.controller;

import com.gabrielapredut.teachpoint.backend.model.Instructor;
import com.gabrielapredut.teachpoint.backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.findAllInstructors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long id) {
        Optional<Instructor> instructor = instructorService.findInstructorById(id);
        return instructor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        Instructor savedInstructor = instructorService.saveInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor instructor) {
        if (!instructorService.findInstructorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instructor.setId(id);
        Instructor updatedInstructor = instructorService.saveInstructor(instructor);
        return ResponseEntity.ok(updatedInstructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        if (!instructorService.findInstructorById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
