package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Student;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public class StudentDAO extends DAO<Student, Integer> {
  public static final StudentDAO instance = new StudentDAO();

  public StudentDAO() {
    super(Student.class);
  }

  public Optional<Student> findByMSSV(int mssv) {
    Session session = sessionFactory.openSession();
    Student student =
        (Student)
            session
                .createQuery("FROM Student WHERE mssv = :mssv")
                .setParameter("mssv", mssv)
                .getSingleResult();
    session.close();
    return Optional.of(student);
  }

  public List<Student> findAll(int offset, int limit) {
    Session session = sessionFactory.openSession();
    List<Student> results =
        session
            .createQuery("FROM Student", Student.class)
            .setMaxResults(limit)
            .setFirstResult(offset)
            .getResultList();
    session.close();
    return results;
  }

  public List<Student> findAll(int offset) {
    return this.findAll(offset, 50);
  }

  public List<Student> findByClass(String classCode) {
    Session session = sessionFactory.openSession();
    List<Student> res =
        session
            .createQuery("FROM Student WHERE class_code = :classCode", Student.class)
            .setParameter("classCode", classCode)
            .getResultList();

    session.close();
    return res;
  }
}
