package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.ReCheckDAO;
import com.beohoang98.qlhs.dao.ReCheckMarkDAO;
import com.beohoang98.qlhs.entities.MarkType;
import com.beohoang98.qlhs.entities.ReCheck;
import com.beohoang98.qlhs.entities.ReCheckMark;
import com.beohoang98.qlhs.entities.ReCheckStatus;
import com.beohoang98.qlhs.entities.Student;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MarkService {

  @NotNull
  public static ReCheck createReCheck(String courseCode, Date from, Date to) {
    ReCheck check = new ReCheck();
    check.setCourseCode(courseCode);
    check.setFromDate(from);
    check.setToDate(to);
    Integer id = ReCheckDAO.instance.save(check);
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

    List<Integer> ids = ReCheckDAO.instance.save(checks);
    for (int i = 0, len = checks.size(); i < len; ++i) {
      checks.get(i).setId(ids.get(i));
    }

    return checks;
  }

  public static void updateReCheck(ReCheck check) {
    ReCheckDAO.instance.update(check);
  }

  public static void updateReCheck(List<ReCheck> checks) {
    ReCheckDAO.instance.update(checks);
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
    ReCheckMarkDAO.instance.save(reCheckMark);
  }

  public static void resolveStudentReCheck(@NotNull ReCheckMark reCheckMark) {
    reCheckMark.setStatus(ReCheckStatus.UPDATED);
    ReCheckMarkDAO.instance.save(reCheckMark);
  }
}
