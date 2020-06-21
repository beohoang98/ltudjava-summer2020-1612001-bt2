package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Mark;

public class MarkDAO extends DAO<Mark, String> {
  public static final MarkDAO instance = new MarkDAO();

  public MarkDAO() {
    super(Mark.class);
  }
}
