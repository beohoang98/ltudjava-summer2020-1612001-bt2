package com.beohoang98.qlhs.mapping;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;

public class StudentDto {
  @CsvBindByName(column = "mssv", required = true)
  @CsvNumber("#")
  public int mssv;

  @CsvBindByName(column = "name", required = true)
  public String name;

  @CsvBindByName(column = "gender", required = true)
  public String gender;

  @CsvBindByName(column = "cmnd", required = true)
  public String cmnd;
}
