package com.beohoang98.qlhs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
  @Id
  private String code;
  private String name;

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
    return "Course{" + "code='" + code + '\'' + ", name='" + name + '\'' + '}';
  }
}
