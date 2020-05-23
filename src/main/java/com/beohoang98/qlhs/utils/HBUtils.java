package com.beohoang98.qlhs.utils;

import com.beohoang98.qlhs.App;
import com.beohoang98.qlhs.entities.ClassCourse;
import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class HBUtils {
  private static SessionFactory sessionFactory;

  private synchronized static SessionFactory load() throws IOException {
    Dotenv dotenv = Dotenv.load();
    Properties properties = new Properties();
    properties.load(App.class.getClassLoader().getResourceAsStream("hibernate.properties"));
    properties.setProperty("hibernate.connection.url", dotenv.get("JDBC_DATABASE_URL"));
    return new Configuration()
        .addProperties(properties)
        .addAnnotatedClass(Student.class)
        .addAnnotatedClass(SchoolClass.class)
        .addAnnotatedClass(Course.class)
        .addAnnotatedClass(ClassCourse.class)
        .buildSessionFactory();
  }

  public synchronized static SessionFactory getSessionFactory() throws IOException {
    if (sessionFactory == null) {
      sessionFactory = load();
    }
    return sessionFactory;
  }
}
