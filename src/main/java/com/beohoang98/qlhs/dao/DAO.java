package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.utils.HBUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceContext;

interface DAOInterface<T, ID extends Serializable> {
  Optional<T> findOne(ID id);

  List<T> findAll();

  ID save(@NotNull T entity);

  void update(@NotNull T entity);

  void delete(@NotNull T entity);
}

public class DAO<T, ID extends Serializable> implements DAOInterface<T, ID> {
  protected SessionFactory sessionFactory = HBUtils.getSessionFactory();

  @PersistenceContext protected Session session = sessionFactory.openSession();

  protected Class<T> classType;

  public DAO(Class<T> classType) throws IOException {
    this.classType = classType;
  }

  @Override
  public Optional<T> findOne(ID id) {
    T object = session.get(classType, id);
    return Optional.ofNullable(object);
  }

  @Override
  public List<T> findAll() {
    return session.createQuery("FROM " + classType.getSimpleName(), classType).getResultList();
  }

  @Override
  public ID save(@NotNull T entity) {
    Transaction t = session.beginTransaction();
    ID identifier = (ID) session.save(entity);
    t.commit();
    return identifier;
  }

  @Override
  public void update(@NotNull T entity) {
    Transaction t = session.beginTransaction();
    session.update(entity);
    t.commit();
  }

  @Override
  public void delete(@NotNull T entity) {
    Transaction t = session.beginTransaction();
    session.remove(entity);
    t.commit();
  }
}
