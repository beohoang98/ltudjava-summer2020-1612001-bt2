package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.services.StudentService;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;

import org.jetbrains.annotations.NotNull;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClassDetails extends JPanel implements AncestorListener {
  String classCode;
  Disposable disposable;

  //  JPanel controlPanel = new JPanel();
  JTable coursesTable = new JTable();
  JTable studentTable = new JTable();
  JScrollPane coursesWrapper = new JScrollPane();
  JScrollPane studentsWrapper = new JScrollPane();

  public ClassDetails(String classCode) {
    super();
    this.classCode = classCode;
    makeUI();
    loadData();
    disposable =
        TabState.currentTabObserver
            .filter(context -> context.tab.equals(AvailableTabs.CLASS_DETAILS.name().toLowerCase()))
            .subscribe(context -> this.ifTabChangeContext(context.args[0]));
  }

  void makeUI() {
    setLayout(new BorderLayout());
    JPanel controlPanel = new JPanel();
    JPanel contentPanel = new JPanel();
    add(controlPanel, BorderLayout.NORTH);
    add(contentPanel, BorderLayout.CENTER);

    contentPanel.setLayout(new GridLayout(1, 2));
    contentPanel.add(coursesWrapper);
    contentPanel.add(studentsWrapper);

    coursesTable.setFillsViewportHeight(true);
    coursesTable.setAutoCreateRowSorter(true);
    studentTable.setFillsViewportHeight(true);
    studentTable.setAutoCreateRowSorter(true);

    coursesWrapper.setViewportView(createLoadingLabel());
    studentsWrapper.setViewportView(createLoadingLabel());
  }

  JPanel createLoadingLabel() {
    JPanel container = new JPanel();
    container.setLayout(new BorderLayout());

    URL loadingIconUrl = getClass().getClassLoader().getResource("ajax-loader.gif");
    if (loadingIconUrl != null) {
      container.add(new JLabel("Loading...", new ImageIcon(loadingIconUrl), JLabel.CENTER));
    } else {
      container.add(new JLabel("Loading..."), JLabel.CENTER);
    }

    return container;
  }

  void ifTabChangeContext(String classCode) {
    if (this.classCode.equals(classCode)) {
      return;
    }
    this.classCode = classCode;
    loadData();
  }

  void loadData() {
    loadCourses();
    loadStudents();
  }

  void loadCourses() {}

  void loadStudents() {
    Single.fromCallable(() -> StudentService.findByClass(this.classCode))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .doFinally(
            () -> {
              studentsWrapper.setViewportView(studentTable);
            })
        .subscribe(this::fillStudentsToTable, this::showError);
  }

  void fillStudentsToTable(@NotNull List<Student> students) {
    System.out.println("Loaded students: " + students.size() + " records");
    String[] columns = {
      Messages.Student.MSSV, Messages.Student.NAME, Messages.Student.GENDER, Messages.Student.CMND
    };
    DefaultTableModel model = new DefaultTableModel(columns, 0);

    for (Student student : students) {
      List<?> cells =
          Arrays.asList(
              student.getMSSV(), student.getName(), student.getGender(), student.getCMND());
      model.addRow(cells.toArray());
    }

    studentTable.setModel(model);
  }

  void showError(@NotNull Throwable throwable) {
    // TODO: handle message
    JOptionPane.showMessageDialog(this, "Error", throwable.getMessage(), JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void ancestorAdded(AncestorEvent ancestorEvent) {}

  @Override
  public void ancestorRemoved(AncestorEvent ancestorEvent) {
    if (disposable != null) {
      disposable.dispose();
    }
  }

  @Override
  public void ancestorMoved(AncestorEvent ancestorEvent) {}
}
