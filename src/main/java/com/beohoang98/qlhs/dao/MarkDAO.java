package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Mark;
import java.io.IOException;

public class MarkDAO extends DAO<Mark, String> {
  public MarkDAO() throws IOException {
    super(Mark.class);
  }
}
