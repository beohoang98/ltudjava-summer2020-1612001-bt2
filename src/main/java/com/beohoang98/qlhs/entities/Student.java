package com.beohoang98.qlhs.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity()
@Table(name = "student")
public class Student {
  @Id
  private Integer mssv;
  private String name;
  private String cmnd;

  @Enumerated(EnumType.ORDINAL)
  private Gender gender;

  @OneToMany(targetEntity = SchoolClass.class, fetch = FetchType.EAGER)
  private SchoolClass schoolClass;

  @ManyToMany
  private List<ClassCourse> courses;

  public Integer getMSSV() {
    return mssv;
  }

  public void setMSSV(Integer mssv) {
    this.mssv = mssv;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCMND() {
    return cmnd;
  }

  public void setCMND(String cmnd) {
    this.cmnd = cmnd;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public SchoolClass getSchoolClass() {
    return schoolClass;
  }

  public void setSchoolClass(SchoolClass schoolClass) {
    this.schoolClass = schoolClass;
  }

  public List<ClassCourse> getCourses() {
    return courses;
  }

  public void setCourses(List<ClassCourse> courses) {
    this.courses = courses;
  }
}
