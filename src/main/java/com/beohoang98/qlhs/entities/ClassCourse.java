package com.beohoang98.qlhs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ClassCourse {
  @ManyToOne @Id private SchoolClass schoolClass;
  @ManyToOne @Id private Course course;

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
