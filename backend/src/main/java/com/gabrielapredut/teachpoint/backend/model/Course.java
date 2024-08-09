package com.gabrielapredut.teachpoint.backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    
    public Course() {
    }

    public Course(String title, String description, Instructor instructor) {
        this.title = title;
        this.description = description;
        this.instructor = instructor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    // Add a single lecture
    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
        lecture.setCourse(this); // Set the back-reference
    }

    // Remove a single lecture
    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
        lecture.setCourse(null); // Clear the back-reference
    }
}
