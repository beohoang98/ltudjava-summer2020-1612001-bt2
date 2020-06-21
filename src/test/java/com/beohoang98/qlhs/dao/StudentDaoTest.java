package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Gender;
import com.beohoang98.qlhs.entities.Student;
import java.io.IOException;
import java.util.Optional;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StudentDaoTest {
  static StudentDAO studentDAO;

  @BeforeClass
  public static void beforeAll() throws IOException {
    studentDAO = new StudentDAO();
  }

  @Test
  public void shouldInsert() {
    Student student = new Student();
    student.setMssv(1612001);
    student.setName("An");
    student.setGender(Gender.MALE);
    student.setCmnd("123456789");
    int mssv = studentDAO.save(student);
    Assert.assertEquals(1612001, mssv);
  }

  @Test
  public void shouldUpdate() {
    Student student = new Student();
    student.setMssv(1612027);
    student.setName("Hai Au");
    student.setGender(Gender.FEMALE);
    student.setCmnd("12345678910");
    int mssv = studentDAO.save(student);

    Optional<Student> get01 = studentDAO.findOne(mssv);
    Assert.assertTrue(get01.isPresent());
    Assert.assertEquals("Hai Au", get01.get().getName());

    student.setName("Seagull");
    studentDAO.update(student);

    Optional<Student> get02 = studentDAO.findOne(mssv);
    Assert.assertTrue(get02.isPresent());
    Assert.assertEquals("Seagull", get02.get().getName());
  }
}
