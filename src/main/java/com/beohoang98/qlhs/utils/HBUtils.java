package com.beohoang98.qlhs.utils;

import com.beohoang98.qlhs.App;
import com.beohoang98.qlhs.entities.Account;
import com.beohoang98.qlhs.entities.ClassCourse;
import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.PersistenceContext;

import io.github.cdimascio.dotenv.Dotenv;

public class HBUtils {
  @PersistenceContext private static SessionFactory sessionFactory;

  private static synchronized SessionFactory load() throws IOException {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
    Properties properties = new Properties();
    properties.load(App.class.getClassLoader().getResourceAsStream("hibernate.properties"));
    if (!properties.containsKey("hibernate.connection.url")) {
      properties.setProperty("hibernate.connection.url", dotenv.get("JDBC_DATABASE_URL"));
    }
    if (!properties.containsKey("hibernate.dialect")) {
      properties.setProperty("hibernate.dialect", dotenv.get("JDBC_DRIVER"));
    }
    return new Configuration()
        .addProperties(properties)
        .addAnnotatedClass(Student.class)
        .addAnnotatedClass(SchoolClass.class)
        .addAnnotatedClass(Course.class)
        .addAnnotatedClass(ClassCourse.class)
        .addAnnotatedClass(Account.class)
        .buildSessionFactory();
  }

  public static synchronized SessionFactory getSessionFactory() throws IOException {
    if (sessionFactory == null || sessionFactory.isClosed()) {
      sessionFactory = load();
    }

    return sessionFactory;
  }

  public static void down() {
    if (sessionFactory != null) sessionFactory.close();
  }
}
