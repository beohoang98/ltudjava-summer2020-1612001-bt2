package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Course;

public class CourseDAO extends DAO<Course, String> {
  public static final CourseDAO instance = new CourseDAO();

  public CourseDAO() {
    super(Course.class);
  }
}
