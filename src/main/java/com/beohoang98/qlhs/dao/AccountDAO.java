package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.entities.Account;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.NoResultException;

public class AccountDAO extends DAO<Account, Long> {
  public AccountDAO() throws IOException {
    super(Account.class);
  }

  @Override
  public Optional<Account> findOne(Long id) {
    return Optional.of(session.get(Account.class, id));
  }

  public Optional<Account> findByUsername(@NotNull String username) {
    try {
      Account account =
          session
              .createQuery("SELECT a FROM Account a WHERE username = :username", Account.class)
              .setParameter("username", username)
              .getSingleResult();
      return Optional.of(account);
    } catch (NoResultException exception) {
      return Optional.empty();
    }
  }
}
