package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.utils.HBUtils;

import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StudentDAO implements DAO<Student, String> {
  Session session = HBUtils.getSessionFactory().openSession();

  public StudentDAO() throws IOException {}

  @Override
  public Optional<Student> findOne(String mssv) {
    Student student = session.get(Student.class, mssv);
    return Optional.of(student);
  }

  @Override
  public List<Student> findAll() {
    return session.createQuery("SELECT s FROM Student s", Student.class).getResultList();
  }

  @Override
  public boolean save(@NotNull Student entity) {
    return session.save(entity) != null;
  }

  @Override
  public void update(@NotNull Student entity) {
    session.update(entity);
  }

  @Override
  public void delete(@NotNull Student entity) {
    session.remove(entity);
  }
}
