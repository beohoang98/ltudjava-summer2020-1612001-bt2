package com.beohoang98.qlhs.mapping;

import com.opencsv.bean.CsvBindByName;

public class CourseDto {
  @CsvBindByName(required = true)
  public String code;

  @CsvBindByName(required = true)
  public String name;

  @CsvBindByName(required = true)
  public String room;
}
