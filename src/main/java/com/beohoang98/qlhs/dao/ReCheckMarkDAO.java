package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.ReCheckMark;

import java.io.IOException;

public class ReCheckMarkDAO extends DAO<ReCheckMark, String> {
  public ReCheckMarkDAO() throws IOException {
    super(ReCheckMark.class);
  }
}
