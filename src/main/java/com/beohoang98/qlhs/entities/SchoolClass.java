package com.beohoang98.qlhs.entities;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "class")
public class SchoolClass {
  @Id private String code;

  @OneToMany(fetch = FetchType.LAZY, targetEntity = Student.class)
  private List<Student> students;

  @Transient private int count;

  public SchoolClass() {}

  public SchoolClass(String code, @NotNull BigInteger count) {
    this.code = code;
    this.count = count.intValue();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getCount() {
    return count;
  }

  public List<Student> getStudents() {
    return students;
  }
}
