/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.beohoang98.qlhs;

import com.beohoang98.qlhs.services.AuthService;
import com.beohoang98.qlhs.ui.Home;
import com.beohoang98.qlhs.ui.Login;
import com.beohoang98.qlhs.utils.AppConfig;
import com.beohoang98.qlhs.utils.HBUtils;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

  public static Logger logger = LogManager.getLogger(App.class);
  static Login login;
  static Home home;

  public static void main(String[] args) throws IOException {
    Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    bootstrap();
    try {
      UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(
        () -> {
          Login login = new Login();
          login.setVisible(true);
          login.setLoginSuccessHandler(
              () -> {
                System.out.println("Login success");
                login.setVisible(false);
                login.dispose();
                showHome();
              });
        });
  }

  public static void bootstrap() {
    // check connection
    String defaultAdminEmail = AppConfig.get().getProperty("default_admin.username");
    boolean isAdminExists = AuthService.exists(defaultAdminEmail);
    if (!isAdminExists) {
      String defaultAdminPassword = AppConfig.get().getProperty("default_admin.password");
      AuthService.create(defaultAdminEmail, defaultAdminPassword);
    }
  }

  public static void showHome() {
    home = new Home();
    home.setLogOutHandler(
        () -> {
          Login login = new Login();
          login.setVisible(true);
          home.dispose();
        });
  }

  public static class ShutdownHook extends Thread {

    @Override
    public void run() {
      HBUtils.down();
    }
  }
}
