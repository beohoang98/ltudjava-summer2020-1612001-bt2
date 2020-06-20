/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.entities.Schedule;
import com.beohoang98.qlhs.entities.SchoolClass;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

/**
 *
 * @author luanlc
 */
public class ScheduleDAO extends DAO<Schedule, Integer> {

    public static ScheduleDAO instance = new ScheduleDAO();
    private static Logger logger= LogManager.getLogger(ScheduleDAO.class);
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

    public Schedule findByClass(String classCode) {
        JDBCConnectionException ex = null;
        Schedule schedule = null;
        int retry = 1;
        Session session = sessionFactory.openSession();
        try {
            Criteria crit = session.createCriteria(Schedule.class).add(Restrictions.eq("class_code", classCode));
            List<Schedule> schedules = crit.list();
            if (schedules != null && !schedules.isEmpty()) {
                schedule = schedules.get(0);
            }
            return schedule;
        } catch (JDBCConnectionException e) {
            logger.error("Error get " + retry, e);
            ex = e;
        } finally {
            session.close();
        }

        throw ex;
    }
}
