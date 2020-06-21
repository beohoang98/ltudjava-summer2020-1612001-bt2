package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.StudentDAO;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.mapping.StudentDto;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;

public class StudentService {

  public static List<StudentDto> importFromFile(FileReader reader) throws RuntimeException {
    return new CsvToBeanBuilder<StudentDto>(reader)
        .withThrowExceptions(true)
        .withType(StudentDto.class)
        .build()
        .parse();
  }

  public static Optional<Student> findByMSSV(int mssv) {
    return StudentDAO.instance.findByMSSV(mssv);
  }

  public static List<Student> findAll(int offset, int limit) {
    return StudentDAO.instance.findAll(offset, limit);
  }

  public static List<Student> findByClass(String classCode) {
    return StudentDAO.instance.findByClass(classCode);
  }

  public static void update(Student student) {
    StudentDAO.instance.update(student);
  }
}
