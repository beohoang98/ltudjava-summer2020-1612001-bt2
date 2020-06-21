package com.beohoang98.qlhs.ui.dialog;

import com.beohoang98.qlhs.mapping.CourseDto;
import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.utils.Popup;
import com.opencsv.exceptions.CsvException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
    loadFile();
  }

  void initComponents() {
    Container container = getContentPane();
    container.setLayout(new BorderLayout());

    table = new JTable();
    table.setAutoCreateColumnsFromModel(true);
    scrollPane = new JScrollPane(table);

    container.add(scrollPane, BorderLayout.CENTER);
    pack();
  }

  void loadFile() {
    try {
      List<CourseDto> courseDtos = CourseService.importFromCsv(this.csvFile);

      String[] cols = {"code", "name", "room"};
      DefaultTableModel model = new DefaultTableModel(cols, 0);

      for (CourseDto courseDto : courseDtos) {
        List<Object> cells = Arrays.asList(courseDto.code, courseDto.name, courseDto.room);
        model.addRow(cells.toArray());
      }

      table.setModel(model);
    } catch (FileNotFoundException | CsvException e) {
      Popup.on(this).error(e);
    }
  }
}
