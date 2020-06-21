package com.beohoang98.qlhs.mapping;

import com.opencsv.bean.CsvBindByName;

public class CourseDto {

  @CsvBindByName(required = true, column = "course_code")
  public String code;

  @CsvBindByName(required = true)
  public String name;

  @CsvBindByName(required = true)
  public String room;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getRoom() {
    return room;
  }
}
