package com.beohoang98.qlhs.entities;

import com.beohoang98.qlhs.mapping.CourseDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "course")
public class Course {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_code")
  SchoolClass schoolClass;

  @Id
  @Column(name = "code")
  private String code;

  @Column(nullable = false)
  @NotBlank
  private String name;

  @OneToMany private List<ReCheck> reChecks = new ArrayList<>();

  public Course() {}

  public Course(String code) {
    this.code = code;
  }

  public Course(@NotNull CourseDto dto) {
    code = dto.code;
    name = dto.name;
  }

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

  public List<ReCheck> getReChecks() {
    return reChecks;
  }
}
