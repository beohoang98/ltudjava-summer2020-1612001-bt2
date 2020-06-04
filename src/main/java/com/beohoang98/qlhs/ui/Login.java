package com.beohoang98.qlhs.ui;

import com.beohoang98.qlhs.services.AuthService;
import com.beohoang98.qlhs.ui.components.PasswordField;
import com.beohoang98.qlhs.ui.components.TextField;
import com.beohoang98.qlhs.ui.styles.AppColor;
import com.beohoang98.qlhs.ui.styles.Margin;

import org.apache.commons.validator.routines.EmailValidator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends JFrame implements ActionListener {
  static String title = "Login";
  JLabel titleLabel;
  TextField userField;
  PasswordField passField;
  JButton submitButton;
  JLabel errorLabel;

  JPanel backgroundPanel;
  JPanel formPanel;

  LoginSuccess loginSuccessHandler;

  public Login() {
    super(title);
    setSize(400, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    backgroundPanel = new GradientFormPanel();
    backgroundPanel.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = GridBagConstraints.REMAINDER;

    createView();
    createForm();

    Container container = getContentPane();
    container.add(backgroundPanel);
    backgroundPanel.add(formPanel);
  }

  void createView() {
    titleLabel = new WhiteLabel(title);
    titleLabel.setFont(new Font("Ubuntu", Font.BOLD, 36));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    userField = new TextField();
    userField.setSize(0, Margin.x2);
    userField.setBorderRadius(Margin.x1);
    userField.addActionListener(this);

    passField = new PasswordField();
    passField.setSize(0, Margin.x2);
    passField.setBorderRadius(Margin.x1);
    passField.addActionListener(this);

    submitButton = new JButton(title);
    submitButton.setBorder(BorderFactory.createEmptyBorder());
    submitButton.setBackground(AppColor.PRIMARY);
    submitButton.setForeground(Color.WHITE);
    submitButton.addActionListener(this);

    errorLabel = new JLabel();
    errorLabel.setForeground(Color.ORANGE);
  }

  void createForm() {
    formPanel = new JPanel();
    formPanel.setOpaque(false);
    formPanel.setLayout(new GridLayout(0, 1, Margin.x0, Margin.x1));
    //    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

    formPanel.setBorder(
        BorderFactory.createEmptyBorder(Margin.x2, Margin.x2, Margin.x2, Margin.x2));
    ;

    formPanel.add(titleLabel);
    formPanel.add(new WhiteLabel("Username"));
    formPanel.add(userField);
    formPanel.add(new WhiteLabel("Password"));
    formPanel.add(passField);
    formPanel.add(Box.createRigidArea(new Dimension(0, Margin.x2)));
    formPanel.add(submitButton);
    formPanel.add(errorLabel);

    Dimension formDM = formPanel.getPreferredSize();
    formDM.width = 300;
    formPanel.setPreferredSize(formDM);
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    errorLabel.setText("");
    System.out.println(actionEvent);
    String error = validateInput();
    if (error != null) {
      errorLabel.setText(error);
      return;
    }
    formPanel.setEnabled(false);
    if (!login()) {
      errorLabel.setText("Login failed");
      formPanel.setEnabled(true);
      return;
    }
    formPanel.setEnabled(true);
    if (loginSuccessHandler != null) {
      loginSuccessHandler.onLoginSuccess();
    }
  }

  public void setLoginSuccessHandler(LoginSuccess loginSuccessHandler) {
    this.loginSuccessHandler = loginSuccessHandler;
  }

  String validateInput() {
    String username = userField.getText();
    String pass = new String(passField.getPassword());
    if (username == null || username.trim().length() == 0) {
      return "username is empty";
    }
    if (pass.trim().length() == 0) {
      return "password is empty";
    }
    if (!EmailValidator.getInstance().isValid(username)) {
      return "email invalid";
    }
    return null;
  }

  boolean login() {
    String username = userField.getText();
    String pass = new String(passField.getPassword());
    return AuthService.verify(username, pass);
  }

  public static class GradientFormPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics graphics) {
      super.paintComponent(graphics);
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setRenderingHint(
          RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      int w = getWidth();
      int h = getHeight();
      Color start = AppColor.PRIMARY;
      Color end = AppColor.SECONDARY;
      GradientPaint paint = new GradientPaint(0, 0, start, 0, h, end);
      graphics2D.setPaint(paint);
      graphics2D.fillRect(0, 0, w, h);
    }
  }

  public static class WhiteLabel extends JLabel {
    public WhiteLabel(String text) {
      super(text);
      setForeground(Color.WHITE);
    }
  }

  public interface LoginSuccess {
    void onLoginSuccess();
  }
}
