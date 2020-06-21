package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.ui.components.DataTable;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.utils.Popup;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class CourseDetails extends JPanel implements AncestorListener {
  String courseCode;
  DataTable studentDataTable;

  JLabel codeLabel = new JLabel();
  JTextField nameField = new JTextField();

  Disposable courseCodeListener = Disposable.empty();

  public CourseDetails(String courseCode) {
    super();
    this.courseCode = courseCode;
    initComponents();
    loadData();
    courseCodeListener =
        TabState.currentTabObserver
            .filter(context -> AvailableTabs.COURSE_DETAILS.equals(context.tab))
            .subscribe(
                context -> {
                  this.courseCode = context.args[0];
                  loadData();
                });
  }

  void initComponents() {
    Map<String, String> columns = new LinkedHashMap<>();
    columns.put(Messages.t("student.mssv"), "mssv");
    columns.put(Messages.t("student.name"), "mssv");
    columns.put(Messages.t("student.gender"), "gender");
    studentDataTable = new DataTable(columns);

    JPanel header = new JPanel();
    header.setLayout(new FlowLayout(FlowLayout.CENTER));
    header.add(new JLabel(Messages.t("course.code")));
    header.add(codeLabel);
    header.add(new JLabel(Messages.t("course.name")));
    header.add(nameField);

    JPanel body = new JPanel();
    body.setLayout(new GridLayout(1, 2));
    body.add(studentDataTable);

    setLayout(new BorderLayout());
    add(header, BorderLayout.NORTH);
    add(body, BorderLayout.CENTER);
  }

  void loadData() {
    studentDataTable.setLoading(true);
    Single.fromCallable(() -> CourseService.getById(courseCode, true))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .doFinally(
            () -> {
              studentDataTable.setLoading(false);
            })
        .map(
            course -> {
              codeLabel.setText(course.getCode());
              nameField.setText(course.getName());
              return new ArrayList<>(course.getStudents());
            })
        .subscribe(studentDataTable::setData, this::showError);
  }

  void showError(Throwable throwable) {
    Popup.on(this).error(throwable);
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
    loadData();
  }

  @Override
  public void ancestorAdded(AncestorEvent ancestorEvent) {}

  @Override
  public void ancestorRemoved(AncestorEvent ancestorEvent) {
    courseCodeListener.dispose();
  }

  @Override
  public void ancestorMoved(AncestorEvent ancestorEvent) {}
}
