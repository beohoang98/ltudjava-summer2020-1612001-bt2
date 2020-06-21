/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.ScheduleDAO;
import com.beohoang98.qlhs.entities.Schedule;
import java.util.List;

public class ScheduleService {
  public static ScheduleService instance = new ScheduleService();

  public Integer save(Schedule schedule) {
    return ScheduleDAO.instance.save(schedule);
  }

  public Integer save(String class_code, String course_code, String room) {
    return ScheduleDAO.instance.save(class_code, course_code, room);
  }

  public void update(Schedule schedule) {
    ScheduleDAO.instance.update(schedule);
  }

  public List<Schedule> findAll() {
    return ScheduleDAO.instance.findAll();
  }

  public Schedule findByClass(String code) {
    return ScheduleDAO.instance.findByClass(code);
  }
}
