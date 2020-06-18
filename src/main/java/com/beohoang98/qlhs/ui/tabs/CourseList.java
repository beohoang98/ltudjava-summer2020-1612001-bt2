package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.ui.components.DataTable;
import com.beohoang98.qlhs.ui.dialog.AddCourse;
import com.beohoang98.qlhs.ui.messages.Messages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CourseList extends JPanel implements AncestorListener {
  JButton addCourseBtn = new JButton(Messages.Button.ADD);
  DataTable<Course> courseDataTable;
  Disposable loadDataState = Disposable.empty();

  public CourseList() {
    super();
    initComponents();
    loadData();
  }

  void initComponents() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder());

    Map<String, String> columns = new LinkedHashMap<>(); // LinkedHashMap for
    // ordered insert key
    columns.put(Messages.t("course.code"), "code");
    columns.put(Messages.t("course.name"), "code");
    courseDataTable = new DataTable<>(Course.class, columns);

    JPanel control = new JPanel();
    control.setLayout(new FlowLayout(FlowLayout.LEFT));
    control.add(addCourseBtn);
    addCourseBtn.addActionListener(this::onAddClick);

    add(control, BorderLayout.NORTH);
    add(courseDataTable, BorderLayout.CENTER);
  }

  void loadData() {
    courseDataTable.setLoading(true);
    loadDataState =
        Single.fromCallable(CourseService::getAllCourses)
              .subscribeOn(Schedulers.io())
              .observeOn(Schedulers.single())
              .doFinally(() -> courseDataTable.setLoading(false))
              .subscribe(this::fillDataToTable, this::showError);
  }

  void fillDataToTable(List<Course> courses) {
    courseDataTable.setData(courses);
  }

  void showError(Throwable throwable) {
  }

  void onAddClick(ActionEvent actionEvent) {
    JDialog addDialog = new AddCourse(this);
    addDialog.setVisible(true);
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
