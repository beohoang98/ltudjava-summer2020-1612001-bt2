package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.ui.components.StudentTable;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class StudentMain extends JPanel {
  StudentTable table = new StudentTable();

  public StudentMain() {
    setLayout(new BorderLayout());
    add(table, BorderLayout.CENTER);
  }
}
