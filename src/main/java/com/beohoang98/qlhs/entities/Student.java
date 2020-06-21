package com.beohoang98.qlhs.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity()
@Table(name = "student")
public class Student {
  @Id private Integer mssv;

  @NotBlank private String name;

  @Column(nullable = false)
  @NotBlank
  private String cmnd;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  @NotBlank
  private Gender gender;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = SchoolClass.class)
  @JoinColumn(name = "class_code")
  private SchoolClass schoolClass;

  @ManyToMany(mappedBy = "students")
  private Set<Course> courses = new HashSet<>();

  @OneToMany private List<ReCheckMark> reCheckMarkList;

  public List<ReCheckMark> getReCheckMarkList() {
    return reCheckMarkList;
  }

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

  public Set<Course> getCourses() {
    return courses;
  }
}
