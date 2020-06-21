package com.beohoang98.qlhs.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mark implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "course_code")
  private Course course;

  @Id
  @ManyToOne
  @JoinColumn(name = "mssv", referencedColumnName = "mssv")
  private Student student;

  @Column(nullable = false)
  private float midterm = 0f;

  @Column(nullable = false, name = "final_term")
  private float finalTerm = 0f;

  @Column(nullable = false)
  private float other = 0f;

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public float getMidterm() {
    return midterm;
  }

  public void setMidterm(float midterm) {
    this.midterm = midterm;
  }

  public float getFinalTerm() {
    return finalTerm;
  }

  public void setFinalTerm(float finalTerm) {
    this.finalTerm = finalTerm;
  }

  public float getOther() {
    return other;
  }

  public void setOther(float other) {
    this.other = other;
  }
}
