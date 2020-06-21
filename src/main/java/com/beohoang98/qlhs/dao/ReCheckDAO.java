package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.ReCheck;

public class ReCheckDAO extends DAO<ReCheck, Integer> {
  public static final ReCheckDAO instance = new ReCheckDAO();

  public ReCheckDAO() {
    super(ReCheck.class);
  }
}
