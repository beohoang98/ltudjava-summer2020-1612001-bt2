package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.mapping.StudentDto;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

public class StudentService {
  public static List<StudentDto> importFromFile(FileReader reader) {
    return new CsvToBeanBuilder<StudentDto>(reader).withType(StudentDto.class).build().parse();
  }
}
