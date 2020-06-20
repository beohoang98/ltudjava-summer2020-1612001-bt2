package com.beohoang98.qlhs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReCheckMark {
  @Id
  @ManyToOne
  @JoinColumn(name = "check_id")
  private ReCheck check;

  @Id
  @ManyToOne
  @JoinColumn(name = "mssv", referencedColumnName = "mssv")
  private Student student;

  @Column(name = "expected_mark", nullable = false)
  private float expectedMark = 0f;

  @Column(nullable = false, name = "mark_type")
  @Enumerated(EnumType.STRING)
  private MarkType markType;

  private String reason;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ReCheckStatus status = ReCheckStatus.NOT_UPDATED;

  public ReCheck getCheck() {
    return check;
  }

  public void setCheck(ReCheck check) {
    this.check = check;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public float getExpectedMark() {
    return expectedMark;
  }

  public void setExpectedMark(float expectedMark) {
    this.expectedMark = expectedMark;
  }

  public MarkType getMarkType() {
    return markType;
  }

  public void setMarkType(MarkType markType) {
    this.markType = markType;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public ReCheckStatus getStatus() {
    return status;
  }

  public void setStatus(ReCheckStatus status) {
    this.status = status;
  }
}
