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

  public boolean addStudents(String classCode, List<Student> students) {
    Optional<SchoolClass> schoolClassOptional = findOne(classCode);
    SchoolClass schoolClass;
    if (!schoolClassOptional.isPresent()) {
      schoolClass = new SchoolClass();
      schoolClass.setCode(classCode);
      save(schoolClass);
    } else {
      schoolClass = schoolClassOptional.get();
    }

    Transaction t = session.beginTransaction();
    students.forEach(
        student -> {
          student.setSchoolClass(schoolClass);
          session.saveOrUpdate(student);
        });
    t.commit();
    return true;
  }
}
