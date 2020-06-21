package com.beohoang98.qlhs.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nullable;

public class ImportCourseFromCSVDialog extends JDialog {
  JTable table;
  JScrollPane scrollPane;
  JTextField classCode;
  File csvFile;

  public ImportCourseFromCSVDialog(@Nullable Component parent, File file) {
    super();
    this.csvFile = file;
    setLocationRelativeTo(parent);
    initComponents();
  }

  void initComponents() {
    Container container = getContentPane();
    container.setLayout(new BorderLayout());

    table = new JTable();
    scrollPane = new JScrollPane(table);

    container.add(scrollPane, BorderLayout.CENTER);
  }
}
