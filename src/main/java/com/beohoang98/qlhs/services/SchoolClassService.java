package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.SchoolClassDAO;
import com.beohoang98.qlhs.entities.Gender;
import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.mapping.StudentDto;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class SchoolClassService {
  static SchoolClassDAO schoolClassDAO;

  static {
    try {
      schoolClassDAO = new SchoolClassDAO();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public static @NotNull SchoolClass create(@NotNull SchoolClass schoolClass) {
    schoolClassDAO.save(schoolClass);
    return schoolClass;
  }

  public static boolean addStudents(@NotNull String classCode, @NotNull List<StudentDto> list) {
    List<Student> students =
        list.stream()
            .map(
                studentDto -> {
                  Student student = new Student();
                  student.setMSSV(studentDto.mssv);
                  student.setName(studentDto.name);
                  student.setCMND(studentDto.cmnd);
                  student.setGender(studentDto.gender.equals("nam") ? Gender.MALE : Gender.FEMALE);
                  return student;
                })
            .collect(Collectors.toList());
    return schoolClassDAO.addStudents(classCode, students);
  }

  public static List<SchoolClass> findAll(boolean withCount) {
    if (withCount) {
      return schoolClassDAO.findAllWithStudentCount();
    }
    return schoolClassDAO.findAll();
  }
}
