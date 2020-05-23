package com.beohoang98.qlhs.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class SchoolClass {
  @Id private String code;
}
