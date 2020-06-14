package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.services.StudentService;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudentTable extends JScrollPane {
  JTable table = new JTable();
  JPanel loadingLabel = new JPanel();

  public StudentTable() {
    super();
    makeUI();
    load();
  }

  void makeUI() {
    table.setFillsViewportHeight(true);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    loadingLabel.setLayout(new BorderLayout());
    loadingLabel.add(new JLabel("Loading ..."), BorderLayout.CENTER);
  }

  void load(int offset, int limit) {
    String[] columns = {"MSSV", "Ho Ten", "Gioi Tinh", "CMND", "Lop"};
    DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
    table.setModel(tableModel);
    this.showLoading();
    Flowable.fromCallable(() -> StudentService.findAll(offset, limit))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .flatMapIterable(students -> students)
        .doFinally(
            () -> {
              setViewportView(table);
            })
        .subscribe(
            student -> {
              List<?> cells =
                  Arrays.asList(
                      student.getMSSV(),
                      student.getName(),
                      student.getGender(),
                      student.getCMND(),
                      student.getSchoolClass().getCode());
              tableModel.addRow(cells.toArray());
            });
  }

  void load() {
    load(0, 50);
  }

  void showLoading() {
    System.out.println("Loading...");
    setViewportView(loadingLabel);
  }
}
