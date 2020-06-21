package com.beohoang98.qlhs.dao;

import static org.junit.Assert.*;

import com.beohoang98.qlhs.entities.Account;
import com.beohoang98.qlhs.utils.HBUtils;
import java.io.IOException;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTest {
  static AccountDAO accountDAO;

  @Rule public ExpectedException thrown = ExpectedException.none();

  @BeforeClass
  public static void up() throws IOException {
    accountDAO = new AccountDAO();
  }

  @AfterClass
  public static void down() {
    HBUtils.down();
  }

  @Test
  public void _01_shouldCreateAccount() {
    Account account = new Account();
    String username = "giaovu";
    account.setUsername(username);
    account.setPassword("giaovu");
    assertNotNull(accountDAO.save(account));

    Optional<Account> findAccount = accountDAO.findByUsername(username);
    assertTrue(findAccount.isPresent());
  }

  /** Don't run this test alone */
  @Test
  public void _02_shouldThrowIfExist() {
    Account account = new Account();
    String username = "giaovu";
    account.setUsername(username);
    account.setPassword("giaovu");
    thrown.expect(ConstraintViolationException.class);

    accountDAO.save(account);
  }
}
