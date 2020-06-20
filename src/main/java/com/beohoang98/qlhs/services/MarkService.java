package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.MarkDAO;
import com.beohoang98.qlhs.dao.ReCheckDAO;
import com.beohoang98.qlhs.dao.ReCheckMarkDAO;
import com.beohoang98.qlhs.entities.MarkType;
import com.beohoang98.qlhs.entities.ReCheck;
import com.beohoang98.qlhs.entities.ReCheckMark;
import com.beohoang98.qlhs.entities.ReCheckStatus;
import com.beohoang98.qlhs.entities.Student;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MarkService {
  static ReCheckDAO reCheckDAO;
  static MarkDAO markDAO;
  static ReCheckMarkDAO reCheckMarkDAO;

  static {
    try {
      reCheckDAO = new ReCheckDAO();
      markDAO = new MarkDAO();
      reCheckMarkDAO = new ReCheckMarkDAO();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  @NotNull
  public static ReCheck createReCheck(String courseCode, Date from, Date to) {
    ReCheck check = new ReCheck();
    check.setCourseCode(courseCode);
    check.setFromDate(from);
    check.setToDate(to);
    Integer id = reCheckDAO.save(check);
    check.setId(id);
    return check;
  }

  @NotNull
  public static List<ReCheck> createReCheck(
      @NotNull List<String> courseCodeList, Date from, Date to) {
    List<ReCheck> checks =
        courseCodeList.stream()
            .map(
                code -> {
                  ReCheck check = new ReCheck();
                  check.setCourseCode(code);
                  check.setFromDate(from);
                  check.setToDate(to);
                  return check;
                })
            .collect(Collectors.toList());

    List<Integer> ids = reCheckDAO.save(checks);
    for (int i = 0, len = checks.size(); i < len; ++i) {
      checks.get(i).setId(ids.get(i));
    }

    return checks;
  }

  public static void updateReCheck(ReCheck check) {
    reCheckDAO.update(check);
  }

  public static void updateReCheck(List<ReCheck> checks) {
    reCheckDAO.update(checks);
  }

  public static void studentMakeReCheck(
      @NotNull Student student,
      @NotNull ReCheck reCheck,
      float mark,
      @NotNull MarkType type,
      @Nullable String reason) {
    ReCheckMark reCheckMark = new ReCheckMark();
    reCheckMark.setCheck(reCheck);
    reCheckMark.setStudent(student);
    reCheckMark.setMarkType(type);
    reCheckMark.setReason(reason);
    reCheckMark.setExpectedMark(mark);
    reCheckMarkDAO.save(reCheckMark);
  }

  public static void resolveStudentReCheck(@NotNull ReCheckMark reCheckMark) {
    reCheckMark.setStatus(ReCheckStatus.UPDATED);
  }
}
