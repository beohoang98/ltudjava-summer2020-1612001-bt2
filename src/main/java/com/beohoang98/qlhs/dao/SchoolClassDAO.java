package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;

import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SchoolClassDAO extends DAO<SchoolClass, String> {
  public SchoolClassDAO() throws IOException {
    super(SchoolClass.class);
  }

  public boolean addStudent(String classCode, Student student) {
    Optional<SchoolClass> schoolClassOptional = findOne(classCode);
    if (!schoolClassOptional.isPresent()) {
      return false;
    }
    SchoolClass schoolClass = schoolClassOptional.get();
    student.setSchoolClass(schoolClass);

    Transaction transaction = session.beginTransaction();
    session.save(student);
    transaction.commit();
    return true;
  }

  public boolean addStudents(String classCode, List<Student> students) {
    Optional<SchoolClass> schoolClassOptional = findOne(classCode);
    if (!schoolClassOptional.isPresent()) {
      return false;
    }

    Transaction t = session.beginTransaction();
    students.forEach(
        student -> {
          student.setSchoolClass(schoolClassOptional.get());
          session.saveOrUpdate(student);
        });
    t.commit();
    return true;
  }
}
