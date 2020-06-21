package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.Transaction;

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

  public List<SchoolClass> findAllWithStudentCount() {
    List<Object[]> results =
        session
            .createSQLQuery(
                "SELECT c.code, count(s.mssv) FROM \"class\" c JOIN \"student\" s ON s.class_code = c.code GROUP BY c.code")
            .getResultList();

    List<SchoolClass> mapped =
        results.stream()
            .map(objects -> new SchoolClass((String) objects[0], (BigInteger) objects[1]))
            .collect(Collectors.toList());

    return mapped;
  }
}
