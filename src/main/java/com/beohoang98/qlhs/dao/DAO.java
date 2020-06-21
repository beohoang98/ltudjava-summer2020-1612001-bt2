package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.utils.HBUtils;
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

interface DAOInterface<T, ID extends Serializable> {

  Optional<T> findOne(ID id);

  List<T> findAll();

  ID save(@NotNull T entity);

  T update(@NotNull T entity);

  void delete(@NotNull T entity);
}

public class DAO<T, ID extends Serializable> implements DAOInterface<T, ID> {

  private static Logger logger = LogManager.getLogger(DAO.class);
  protected SessionFactory sessionFactory;

  @PersistenceContext protected Session readSession;

  protected Class<T> classType;

  public DAO(Class<T> classType) {
    this.classType = classType;
    try {
      sessionFactory = HBUtils.getSessionFactory();
      readSession = sessionFactory.openSession();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Override
  public Optional<T> findOne(ID id) {
    Session session = sessionFactory.openSession();
    T object = session.get(classType, id);
    session.close();
    return Optional.ofNullable(object);
  }

  @Override
  public List<T> findAll() {
    Session session = sessionFactory.openSession();
    List<T> results =
        session.createQuery("FROM " + classType.getSimpleName(), classType).getResultList();
    session.close();
    return results;
  }

  @Override
  public ID save(@NotNull T entity) {
    Session session = sessionFactory.openSession();
    Transaction t = session.beginTransaction();
    ID identifier = (ID) session.save(entity);
    t.commit();
    session.close();
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
  public T update(@NotNull T entity) {
    Session session = sessionFactory.openSession();
    Transaction t = session.beginTransaction();
    T res = (T) session.merge(entity);
    t.commit();
    session.close();
    return res;
  }

  public void update(@NotNull List<T> entities) {
    Session session = sessionFactory.openSession();
    Transaction t = session.beginTransaction();
    for (T entity : entities) {
      session.update(entity);
    }
    t.commit();
    session.close();
  }

  @Override
  public void delete(@NotNull T entity) {
    Session session = sessionFactory.openSession();
    Transaction t = session.getTransaction();
    t.begin();
    session.remove(entity);
    t.commit();
    session.close();
  }
}
