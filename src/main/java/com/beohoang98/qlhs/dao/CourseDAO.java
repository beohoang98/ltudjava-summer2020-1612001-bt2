package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Course;
import java.io.IOException;

public class CourseDAO extends DAO<Course, String> {
  public CourseDAO() throws IOException {
    super(Course.class);
  }
}
