package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.ReCheckMark;

public class ReCheckMarkDAO extends DAO<ReCheckMark, String> {
  public static final ReCheckMarkDAO instance = new ReCheckMarkDAO();

  public ReCheckMarkDAO() {
    super(ReCheckMark.class);
  }
}
