/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.beohoang98.qlhs;

import static org.junit.Assert.assertTrue;

import com.beohoang98.qlhs.utils.HBUtils;
import java.io.IOException;
import org.hibernate.SessionFactory;
import org.junit.Test;

public class AppTest {
  @Test
  public void hbUtilsShouldDown() throws IOException {
    SessionFactory factory = HBUtils.getSessionFactory();
    assertTrue(factory.isOpen());
    HBUtils.down();
    assertTrue(factory.isClosed());
  }
}
