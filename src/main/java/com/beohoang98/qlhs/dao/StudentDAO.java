package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Student;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StudentDAO extends DAO<Student, Integer> {
  public StudentDAO() throws IOException {
    super(Student.class);
  }

  public Optional<Student> findByMSSV(int mssv) {
    Student student =
        (Student)
            session
                .createQuery("FROM Student WHERE mssv = :mssv")
                .setParameter("mssv", mssv)
                .getSingleResult();
    return Optional.of(student);
  }

  public List<Student> findAll(int offset, int limit) {
    return (List<Student>)
        session
            .createQuery("FROM Student")
            .setMaxResults(limit)
            .setFirstResult(offset)
            .getResultList();
  }

  public List<Student> findAll(int offset) {
    return this.findAll(offset, 50);
  }
}
