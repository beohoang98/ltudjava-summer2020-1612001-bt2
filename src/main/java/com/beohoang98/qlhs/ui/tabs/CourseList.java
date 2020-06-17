package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.ui.messages.Messages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import io.reactivex.rxjava3.disposables.Disposable;

public class CourseList extends JPanel implements AncestorListener {
  JButton addCourseBtn = new JButton(Messages.Button.ADD);
  JTable coursesTable = new JTable();
  JScrollPane tableWrapper = new JScrollPane();
  Disposable loadDataState = Disposable.empty();

  public CourseList() {
    super();
    makeUI();
    loadData();
  }

  void makeUI() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder());

    JPanel control = new JPanel();
    control.setLayout(new FlowLayout(FlowLayout.LEFT));
    control.add(addCourseBtn);

    add(control, BorderLayout.NORTH);
    add(tableWrapper, BorderLayout.CENTER);

    coursesTable.setAutoCreateRowSorter(true);
    coursesTable.setFillsViewportHeight(true);
    tableWrapper.setViewportView(
        new JLabel(
            Messages.Others.LOADING, new ImageIcon("ajax-loader.gif"),
            SwingConstants.CENTER));
  }

  void loadData() {
    // TODO: load data here

    tableWrapper.setViewportView(coursesTable);
  }

  @Override
  public void ancestorAdded(AncestorEvent ancestorEvent) {
  }

  @Override
  public void ancestorMoved(AncestorEvent ancestorEvent) {
  }

  @Override
  public void ancestorRemoved(AncestorEvent ancestorEvent) {
    loadDataState.dispose();
  }
}
