package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.utils.HBUtils;

import org.hibernate.Session;

import java.io.IOException;
import java.util.Optional;

public class StudentDAO extends DAO<Student, String> {
  Session session = HBUtils.getSessionFactory().getCurrentSession();

  public StudentDAO() throws IOException {
    super(Student.class);
  }

  @Override
  public Optional<Student> findOne(String mssv) {
    Student student = session.get(Student.class, mssv);
    return Optional.of(student);
  }
}
