package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.ReCheck;
import java.io.IOException;

public class ReCheckDAO extends DAO<ReCheck, Integer> {
  public ReCheckDAO() throws IOException {
    super(ReCheck.class);
  }
}
