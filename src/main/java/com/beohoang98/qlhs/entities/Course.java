package com.beohoang98.qlhs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
  @Id private String code;

  private String name;
  private String room;
}
