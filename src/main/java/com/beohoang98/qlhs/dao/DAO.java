package com.beohoang98.qlhs.dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {
  Optional<T> findOne(ID id);
  List<T> findAll();
  boolean save(@NotNull T entity);
  void update(@NotNull T entity);
  void delete(@NotNull T entity);
}
