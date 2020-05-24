package com.beohoang98.qlhs.service;

import com.beohoang98.qlhs.entities.Account;
import com.beohoang98.qlhs.services.AuthService;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthServiceTest {
  @Test
  public void shouldCreateAccount() {
    Account newAccount = AuthService.create("asd", "asd");
    assertNotNull(newAccount.getId());
  }

  @Test
  public void shouldValidAccount() {
    AuthService.create("asd1", "asd1");
    assertTrue(AuthService.verify("asd1", "asd1"));
  }

  @Test
  public void shouldInvalidAccount() {
    AuthService.create("asd2", "asd2");
    assertFalse(AuthService.verify("asd2", "123456"));
    assertFalse(AuthService.verify("123456", "asd2"));
  }
}