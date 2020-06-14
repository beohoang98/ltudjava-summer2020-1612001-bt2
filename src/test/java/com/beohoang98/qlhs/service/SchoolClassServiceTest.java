package com.beohoang98.qlhs.service;

import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.mapping.StudentDto;
import com.beohoang98.qlhs.services.SchoolClassService;
import com.beohoang98.qlhs.services.StudentService;
import com.opencsv.exceptions.CsvException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchoolClassServiceTest {
  static List<StudentDto> studentDtoList;

  @BeforeClass
  public static void beforeAll() throws FileNotFoundException, URISyntaxException, CsvException {
    File file = new File(ClassLoader.getSystemResource("16CTN.csv").toURI());
    FileReader reader = new FileReader(file);
    studentDtoList = StudentService.importFromFile(reader);
  }

  @Test
  public void _01_TestCreateSchoolClass() {
    SchoolClass schoolClass = new SchoolClass();
    schoolClass.setCode("16CTT1");
    Assert.assertNotNull(SchoolClassService.create(schoolClass));
  }

  @Test
  public void _02_TestAddStudents() {
    boolean isSaved = SchoolClassService.addStudents("16CTT1", studentDtoList);
    Assert.assertTrue(isSaved);
  }

  @Test
  public void _03_TestGetStudent() {
    Optional<Student> student = StudentService.findByMSSV(160000);
    Assert.assertTrue(student.isPresent());
    Assert.assertEquals("16CTT1", student.get().getSchoolClass().getCode());
  }
}
