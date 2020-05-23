package com.beohoang98.qlhs.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ClassCourse implements Serializable {
  @Id
  @Column(name = "school_class_id")
  private String schoolClassId;

  @Id
  @Column(name = "course_id")
  private String courseId;

  @ManyToOne
  @JoinColumn(name = "school_class_id")
  private SchoolClass schoolClass;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  public SchoolClass getSchoolClass() {
    return schoolClass;
  }

  public void setSchoolClass(SchoolClass schoolClass) {
    this.schoolClass = schoolClass;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }
}
