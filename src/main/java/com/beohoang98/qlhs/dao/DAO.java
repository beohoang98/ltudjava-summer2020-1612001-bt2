package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.utils.HBUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;

interface DAOInterface<T, ID extends Serializable> {
  Optional<T> findOne(ID id);

  List<T> findAll();

  ID save(@NotNull T entity);

  void update(@NotNull T entity);

  void delete(@NotNull T entity);
}

public class DAO<T, ID extends Serializable> implements DAOInterface<T, ID> {
  private static Logger logger = LogManager.getLogger(DAO.class);
  protected SessionFactory sessionFactory;
  /** for read only, if want to write, create manual */
  @PersistenceContext protected Session session = sessionFactory.openSession();

  protected Class<T> classType;

  public DAO(Class<T> classType) {
    this.classType = classType;
    try {
      sessionFactory = HBUtils.getSessionFactory();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
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
    Session writeSession = sessionFactory.openSession();
    Transaction t = writeSession.beginTransaction();
    ID identifier = (ID) writeSession.save(entity);
    t.commit();
    writeSession.close();
    return identifier;
  }

  public List<ID> save(@NotNull List<T> entities) {
    Session writeSession = sessionFactory.openSession();
    Transaction t = writeSession.beginTransaction();
    List<ID> ids = new ArrayList<>();
    for (T entity : entities) {
      ID id = (ID) writeSession.save(entity);
      ids.add(id);
    }
    t.commit();
    writeSession.close();
    return ids;
  }

  @Override
  public void update(@NotNull T entity) {
    Session writeSession = sessionFactory.openSession();
    Transaction t = writeSession.beginTransaction();
    writeSession.update(entity);
    t.commit();
    writeSession.close();
  }

  public void update(@NotNull List<T> entities) {
    Session writeSession = sessionFactory.openSession();
    Transaction t = writeSession.beginTransaction();
    for (T entity : entities) {
      writeSession.update(entity);
    }
    t.commit();
    writeSession.close();
  }

  @Override
  public void delete(@NotNull T entity) {
    Session writeSession = sessionFactory.openSession();
    Transaction t = writeSession.beginTransaction();
    writeSession.remove(entity);
    t.commit();
    writeSession.close();
  }
}
