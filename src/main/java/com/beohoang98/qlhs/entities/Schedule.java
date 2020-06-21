package com.beohoang98.qlhs.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;

@Entity
@Table(name = "schedule_courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_code", "class_code"})})
@Transactional
public class Schedule implements Serializable {

    @Column(name = "room")
    String room;

    @Column(name = "course_code")
    String courseCode;

    @Column(name = "class_code")
    String classCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_code",
            referencedColumnName = "code",
            insertable = false,
            updatable = false)
    Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "class_code",
            referencedColumnName = "code",
            insertable = false,
            updatable = false)
    SchoolClass schoolClass;

    @ManyToMany(mappedBy = "courseSchedules", cascade = CascadeType.ALL)
    List<Student> students = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Schedule() {
    }

    public Schedule(int id, String room, Course course, SchoolClass schoolClass) {
        this.id = id;
        this.room = room;
        this.course = course;
        this.schoolClass = schoolClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.room);
        hash = 19 * hash + Objects.hashCode(this.course);
        hash = 19 * hash + Objects.hashCode(this.schoolClass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Schedule other = (Schedule) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.room, other.room)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.schoolClass, other.schoolClass)) {
            return false;
        }
        return true;
    }
}
