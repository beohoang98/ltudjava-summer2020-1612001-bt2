package com.beohoang98.qlhs.service;

import com.beohoang98.qlhs.mapping.StudentDto;
import com.beohoang98.qlhs.services.StudentService;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class StudentServiceTest {
  @Test
  public void testImportCSV() throws IOException, URISyntaxException, CsvException {
    URL url = getClass().getClassLoader().getResource("16CTN.csv");
    assert url != null;

    File file = new File(url.toURI());
    FileReader reader = new FileReader(file);

    List<StudentDto> studentDtoList = StudentService.importFromFile(reader);
    Assert.assertEquals(100, studentDtoList.size());

    Assert.assertEquals(160000, studentDtoList.get(0).mssv);
    Assert.assertEquals("Marc Long", studentDtoList.get(0).name);

    reader.close();
  }
}
