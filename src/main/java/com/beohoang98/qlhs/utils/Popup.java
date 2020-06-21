package com.beohoang98.qlhs.utils;

import com.beohoang98.qlhs.ui.messages.Messages;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.jetbrains.annotations.NotNull;

public class Popup {

  Component component;

  @NotNull
  public static Popup on(Component component) {
    Popup popup = new Popup();
    popup.component = component;
    return popup;
  }

  public void error(@NotNull Throwable throwable) {
    throwable.printStackTrace();
    JOptionPane.showMessageDialog(
        component, throwable.getMessage(), Messages.t("error_title"), JOptionPane.ERROR_MESSAGE);
  }

  public void info(String text) {
    JOptionPane.showMessageDialog(component, text);
  }
}
