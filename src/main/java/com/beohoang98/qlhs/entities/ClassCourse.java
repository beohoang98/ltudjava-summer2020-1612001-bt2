package com.beohoang98.qlhs.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class ClassCourse implements Serializable {
  @ManyToOne
  @JoinColumn(name = "school_class_id")
  @Id
  private SchoolClass schoolClass;

  @ManyToOne
  @JoinColumn(name = "course_id")
  @Id
  private Course course;

  @NotEmpty private String room;

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

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }
}
