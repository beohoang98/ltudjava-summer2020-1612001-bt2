package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.StudentDAO;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.mapping.StudentDto;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StudentService {
  static StudentDAO studentDAO;

  static {
    try {
      studentDAO = new StudentDAO();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public static List<StudentDto> importFromFile(FileReader reader) throws RuntimeException {
    return new CsvToBeanBuilder<StudentDto>(reader)
        .withThrowExceptions(true)
        .withType(StudentDto.class)
        .build()
        .parse();
  }

  public static Optional<Student> findByMSSV(int mssv) {
    return studentDAO.findByMSSV(mssv);
  }

  public static List<Student> findAll(int offset, int limit) {
    return studentDAO.findAll(offset, limit);
  }

  public static List<Student> findByClass(String classCode) {
    return studentDAO.findByClass(classCode);
  }
}
