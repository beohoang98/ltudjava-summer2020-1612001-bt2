package com.beohoang98.qlhs.services;

import com.beohoang98.qlhs.dao.AccountDAO;
import com.beohoang98.qlhs.entities.Account;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthService {
  static AccountDAO accountDAO;

  static {
    try {
      accountDAO = new AccountDAO();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  @NotNull
  public static Account create(String email, String password) {
    Account account = new Account();
    account.setEmail(email);
    account.setPassword(password);
    Long id = accountDAO.save(account);
    account.setId(id);
    return account;
  }

  public static boolean verify(String email, String password) {
    Optional<Account> account = accountDAO.findByEmail(email);
    if (!account.isPresent()) {
      return false;
    }
    BCrypt.Result verifyResult =
        BCrypt.verifyer().verify(password.getBytes(), account.get().getPassword().getBytes());
    return verifyResult.verified;
  }

  public static boolean exists(String email) {
    Optional<Account> account = accountDAO.findByEmail(email);
    return account.isPresent();
  }
}
