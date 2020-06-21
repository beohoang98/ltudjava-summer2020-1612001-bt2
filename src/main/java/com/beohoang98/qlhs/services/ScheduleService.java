/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.ScheduleDAO;
import com.beohoang98.qlhs.entities.Schedule;
import com.beohoang98.qlhs.mapping.CourseDto;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ScheduleService {

  public static ScheduleService instance = new ScheduleService();

  public Integer save(Schedule schedule) {
    return ScheduleDAO.instance.save(schedule);
  }

  public Integer save(String class_code, String course_code, String room) {
    return ScheduleDAO.instance.save(class_code, course_code, room);
  }

  public Schedule update(Schedule schedule) {
    return ScheduleDAO.instance.update(schedule);
  }

  public List<Schedule> findAll() {
    return ScheduleDAO.instance.findAll();
  }

  public List<Schedule> findByClass(String code) {
    return ScheduleDAO.instance.findByClass(code);
  }

  public @NotNull List<Schedule> findByCourse(String courseCode) {
    return ScheduleDAO.instance.findByCourse(courseCode);
  }

  public Schedule findByClassCourse(String classCode, String courseCode) {
    return ScheduleDAO.instance.findByCourseClass(classCode, courseCode);
  }

  public void importFrom(List<CourseDto> courseDtos, String classCode) {
    ScheduleDAO.instance.importFrom(courseDtos, classCode, true);
  }
}
