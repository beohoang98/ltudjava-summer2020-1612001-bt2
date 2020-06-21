package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.entities.Schedule;
import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.mapping.CourseDto;
import com.beohoang98.qlhs.services.StudentService;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;
import org.jetbrains.annotations.NotNull;

public class ScheduleDAO extends DAO<Schedule, Integer> {

  private static final Logger logger = LogManager.getLogger(ScheduleDAO.class);
  public static ScheduleDAO instance = new ScheduleDAO();

  public ScheduleDAO() {
    super(Schedule.class);
  }

  public Integer save(String class_code, String course_code, String room) {
    Schedule schedule = new Schedule();
    schedule.setCourse(new Course(course_code));
    SchoolClass clazz = new SchoolClass();
    clazz.setCode(class_code);
    schedule.setSchoolClass(clazz);
    schedule.setRoom(room);
    return save(schedule);
  }

  public List<Schedule> findByClass(String classCode) {
    JDBCConnectionException ex = null;
    Schedule schedule = null;
    int retry = 1;
    try (Session session = sessionFactory.openSession()) {
      Criteria crit =
          session.createCriteria(Schedule.class).add(Restrictions.eq("classCode", classCode));
      List<Schedule> schedules = crit.list();
      return schedules;
    } catch (JDBCConnectionException e) {
      logger.error("Error get " + retry, e);
      ex = e;
    }

    throw ex;
  }

  public Schedule findByCourseClass(String classCode, String courseCode) {
    CriteriaBuilder builder = readSession.getCriteriaBuilder();
    CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
    Root<Schedule> root = query.from(Schedule.class);
    query.where(
        builder.and(
            builder.equal(root.get("courseCode"), courseCode),
            builder.equal(root.get("classCode"), classCode)));
    Schedule res = readSession.createQuery(query).getSingleResult();
    Hibernate.initialize(res.getStudents());
    return res;
  }

  public @NotNull List<Schedule> findByCourse(String code) {
    Session session = sessionFactory.openSession();
    CriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
    Root<Schedule> root = query.from(Schedule.class);
    query.where(builder.equal(root.get("courseCode"), code));
    return session.createQuery(query).getResultList();
  }

  public void importFrom(@NotNull List<CourseDto> courseDtos, String classCode, boolean isOverwrite)
      throws HibernateException {
    Session session = sessionFactory.openSession();
    Transaction t = session.beginTransaction();
    try {
      List<Student> students = StudentService.findByClass(classCode);
      for (CourseDto dto : courseDtos) {
        Course course = new Course(dto);
        session.saveOrUpdate(course);

        Schedule schedule = new Schedule();
        schedule.setCourseCode(dto.code);
        schedule.setClassCode(classCode);
        schedule.setRoom(dto.room);
        schedule.getStudents().addAll(students);

        if (isOverwrite) {
          session.saveOrUpdate(schedule);
        } else {
          session.save(schedule);
        }
      }
      t.commit();
    } catch (HibernateException e) {
      t.rollback();
      throw e;
    } finally {
      session.close();
    }
  }
}
