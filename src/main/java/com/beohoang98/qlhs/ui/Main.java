package com.beohoang98.qlhs.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {
  private JPanel mainPanel;
  private JPanel menu;
  private JPanel content;
  private JButton button1;
  private JButton button2;
  private JScrollPane sidebar;
  private JScrollPane contentBody;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Main");
    frame.setContentPane(new Main().mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }
}
