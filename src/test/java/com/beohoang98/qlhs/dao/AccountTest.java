package com.beohoang98.qlhs.dao;

import com.beohoang98.qlhs.dao.AccountDAO;
import com.beohoang98.qlhs.entities.Account;
import com.beohoang98.qlhs.utils.HBUtils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountTest {
  static AccountDAO accountDAO;

  @BeforeClass
  public static void up() throws IOException {
    accountDAO = new AccountDAO();
  }

  @AfterClass
  public static void down() {
    HBUtils.down();
  }

  @Test
  public void shouldCreateAccount() {
    Account account = new Account();
    String email = "giaovu@hcmus.edu.vn";
    account.setEmail(email);
    account.setPassword("123456");
    assertNotNull(accountDAO.save(account));

    Optional<Account> findAccount = accountDAO.findByEmail(email);
    assertTrue(findAccount.isPresent());
  }
}
