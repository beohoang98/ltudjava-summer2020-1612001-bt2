package com.beohoang98.qlhs.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Course {

  @Id private String code;

  @Column(nullable = false)
  @NotBlank
  private String name;

  @ManyToMany(
      targetEntity = Student.class,
      cascade = {CascadeType.ALL})
  @JoinTable(
      name = "course_student",
      joinColumns = {@JoinColumn(name = "course_code")},
      inverseJoinColumns = {@JoinColumn(name = "student_mssv")})
  private Set<Student> students = new HashSet<>();

  @OneToMany private List<ReCheck> reChecks = new ArrayList<>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return code + " - " + name;
  }

  public Set<Student> getStudents() {
    return students;
  }

  public List<ReCheck> getReChecks() {
    return reChecks;
  }
}
