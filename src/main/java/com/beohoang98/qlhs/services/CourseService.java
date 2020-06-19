package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.CourseDAO;
import com.beohoang98.qlhs.dao.SchoolClassDAO;
import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.mapping.CourseDto;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CourseService {
  static CourseDAO courseDAO;
  static SchoolClassDAO schoolClassDAO;

  static {
    try {
      courseDAO = new CourseDAO();
      schoolClassDAO = new SchoolClassDAO();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public static List<Course> getAllCourses() {
    return courseDAO.findAll();
  }

  @NotNull
  public static List<Course> getByClass(@NotNull String classCode) {
    return new ArrayList<>();
  }

  public static List<Course> getByStudent(int mssv) {
    throw new NotImplementedException();
  }

  @NotNull
  public static Course getById(@NotNull String courseCode, boolean loadStudent)
      throws NoSuchElementException {
    Optional<Course> courseOptional = courseDAO.findOne(courseCode);
    if (!courseOptional.isPresent()) {
      throw new NoSuchElementException(courseCode);
    }
    Course course = courseOptional.get();
    if (loadStudent) {
      Hibernate.initialize(course.getStudents());
    }
    return course;
  }

  @NotNull
  public static Course getById(@NotNull String courseCode) throws NoSuchElementException {
    return getById(courseCode, false);
  }

  @NotNull
  public static Course add(@NotNull String code, @NotNull String name) {
    Course course = new Course();
    course.setCode(code);
    course.setName(name);
    courseDAO.save(course);
    return course;
  }

  public static List<CourseDto> importFromCsv(File csvFile)
      throws FileNotFoundException, RuntimeException, CsvException {
    FileReader fileReader = new FileReader(csvFile);

    return new CsvToBeanBuilder<CourseDto>(fileReader)
        .withThrowExceptions(true)
        .withSeparator(',')
        .withType(CourseDto.class)
        .build()
        .parse();
  }
}
