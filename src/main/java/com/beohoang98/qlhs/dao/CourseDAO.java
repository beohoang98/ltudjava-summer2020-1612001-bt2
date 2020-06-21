package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Course;
import org.hibernate.Session;

public class CourseDAO extends DAO<Course, String> {

  public static final CourseDAO instance = new CourseDAO();

  public CourseDAO() {
    super(Course.class);
  }

  public Course findByIdWithStudents(String code) {
    Session session = sessionFactory.openSession();
    Course course = session.get(Course.class, code);
    session.close();
    return course;
  }
}
