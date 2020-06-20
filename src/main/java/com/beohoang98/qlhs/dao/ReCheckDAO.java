package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.ReCheck;

import java.io.IOException;
import java.util.Date;

public class ReCheckDAO extends DAO<ReCheck, Integer> {
  public ReCheckDAO() throws IOException {
    super(ReCheck.class);
  }
}
