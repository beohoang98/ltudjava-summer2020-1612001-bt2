package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.styles.Margin;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField {
  int borderRadius = 0;

  public PasswordField() {
    super();
    setBorder(BorderFactory
                  .createEmptyBorder(Margin.x1, Margin.x2, Margin.x1, Margin.x2));
  }

  public void setBorderRadius(int borderRadius) {
    this.borderRadius = borderRadius;
  }

  @Override
  protected void paintComponent(final Graphics graphics) {
    int width = getWidth() - getInsets().left - getInsets().right;
    int height = getHeight() - getInsets().top - getInsets().bottom;
    graphics.setColor(getBackground());
    graphics.fillRoundRect(
        getInsets().left, getInsets().top, width, height, borderRadius, borderRadius);
    super.paintComponent(graphics);
  }
}
