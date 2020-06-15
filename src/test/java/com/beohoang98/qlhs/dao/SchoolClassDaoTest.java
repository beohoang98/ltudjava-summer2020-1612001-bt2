package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Gender;
import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;

import org.apache.commons.collections4.IterableUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchoolClassDaoTest {
  static SchoolClassDAO dao;
  static StudentDAO studentDAO;
  static String classCode = "16TEST";

  @BeforeClass
  public static void beforeAll() throws IOException {
    dao = new SchoolClassDAO();
    studentDAO = new StudentDAO();
  }

  @Test
  public void _01_testAddStudents() {
    List<Student> students = new ArrayList<>();
    Student student1 = new Student();
    student1.setName("1");
    student1.setMSSV(1);
    student1.setCMND("1");
    student1.setGender(Gender.MALE);
    students.add(student1);

    boolean res = dao.addStudents(classCode, students);
    Assert.assertTrue(res);

    Optional<Student> getStudent = studentDAO.findByMSSV(1);
    Assert.assertTrue(getStudent.isPresent());
    Assert.assertEquals(classCode, getStudent.get().getSchoolClass().getCode());
  }

  @Test
  public void _02_testFindAllWithCount() {
    List<SchoolClass> classes = dao.findAllWithStudentCount();
    Assert.assertEquals(classCode, classes.get(0).getCode());
    Assert.assertEquals(1, classes.get(0).getCount());
  }

  @Test
  public void _03_testFindByClass() {
    Student student = new Student();
    student.setMSSV(1612009);
    student.setName("Chicken Phuoc An");
    student.setGender(Gender.MALE);
    student.setCMND("1234567891012");

    SchoolClass schoolClass = new SchoolClass();
    schoolClass.setCode(classCode);
    student.setSchoolClass(schoolClass);
    int mssv = studentDAO.save(student);

    List<Student> studentsInClass = studentDAO.findByClass(classCode);
    Assert.assertNotEquals(0, studentsInClass.size());

    Student insertedStudent = IterableUtils.find(studentsInClass, object -> object.getMSSV() == 1612009);
    Assert.assertNotNull(insertedStudent);
  }
}
