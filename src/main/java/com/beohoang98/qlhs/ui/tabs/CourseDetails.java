/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.entities.Schedule;
import com.beohoang98.qlhs.entities.Student;
import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.services.ScheduleService;
import com.beohoang98.qlhs.services.StudentService;
import com.beohoang98.qlhs.ui.dialog.AddStudent;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.utils.Popup;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import org.jetbrains.annotations.NotNull;

/** @author noobcoder */
public class CourseDetails extends javax.swing.JPanel {

  String courseCode;
  Schedule currentCourse;
  Integer currentSelectMssv;

  /** Creates new form CourseDetails */
  public CourseDetails() {
    initComponents();
  }

  public CourseDetails(String courseCode) {
    this.courseCode = courseCode;
    initComponents();
    watchEvent();
  }

  void watchEvent() {
    TabState.currentTabObserver
        .filter(context -> AvailableTabs.COURSE_DETAILS.equals(context.tab))
        .subscribe(
            context -> {
              if (context.args[0] != courseCode) {
                courseCode = context.args[0];
                loadData();
              }
            });
    studentTable.addSelectHandler(
        cells -> {
          if (cells.length == 0) {
            currentSelectMssv = null;
            removeStudent.setEnabled(false);
            return;
          }
          int mssv = (Integer) cells[0];
          currentSelectMssv = mssv;
          removeStudent.setEnabled(true);
        });
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jPanel4 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    nameText = new javax.swing.JTextField();
    saveBtn = new javax.swing.JButton();
    jPanel3 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    classList = new javax.swing.JList<>();
    jPanel5 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    addClass = new javax.swing.JButton();
    removeClass = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    studentTable = new com.beohoang98.qlhs.ui.components.DataTable();
    jPanel6 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    addStudent = new javax.swing.JButton();
    removeStudent = new javax.swing.JButton();

    addAncestorListener(
        new javax.swing.event.AncestorListener() {
          public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            formAncestorAdded(evt);
          }

          public void ancestorMoved(javax.swing.event.AncestorEvent evt) {}

          public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            formAncestorRemoved(evt);
          }
        });
    setLayout(new java.awt.BorderLayout());

    jLabel3.setText("Tên môn");

    nameText.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            nameTextActionPerformed(evt);
          }
        });

    saveBtn.setText("Lưu");
    saveBtn.setEnabled(false);
    saveBtn.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            saveBtnActionPerformed(evt);
          }
        });

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel4Layout
                    .createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        nameText,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        380,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                    .addComponent(saveBtn)
                    .addContainerGap()));
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel4Layout
                    .createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(
                        jPanel4Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(
                                jLabel3,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                44,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(
                                nameText,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveBtn))
                    .addContainerGap()));

    add(jPanel4, java.awt.BorderLayout.NORTH);

    jPanel3.setLayout(new java.awt.GridBagLayout());

    jPanel1.setLayout(new java.awt.BorderLayout());

    classList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    classList.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseClicked(java.awt.event.MouseEvent evt) {
            classListMouseClicked(evt);
          }
        });
    jScrollPane1.setViewportView(classList);

    jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    jLabel1.setText("Lớp");

    addClass.setText("+");
    addClass.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            addClassActionPerformed(evt);
          }
        });

    removeClass.setBackground(new java.awt.Color(102, 0, 0));
    removeClass.setText("-");
    removeClass.setEnabled(false);

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel5Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addComponent(removeClass)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addClass)
                    .addContainerGap()));
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel5Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jPanel5Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(addClass)
                            .addComponent(removeClass))
                    .addContainerGap()));

    jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
    gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.4;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
    jPanel3.add(jPanel1, gridBagConstraints);

    jPanel2.setLayout(new java.awt.BorderLayout());

    studentTable.addFocusListener(
        new java.awt.event.FocusAdapter() {
          public void focusLost(java.awt.event.FocusEvent evt) {
            studentTableFocusLost(evt);
          }
        });
    jPanel2.add(studentTable, java.awt.BorderLayout.CENTER);

    jLabel2.setText("Sinh viên");

    addStudent.setText("+");
    addStudent.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            addStudentActionPerformed(evt);
          }
        });

    removeStudent.setBackground(new java.awt.Color(102, 0, 0));
    removeStudent.setForeground(new java.awt.Color(0, 0, 0));
    removeStudent.setText("-");
    removeStudent.setEnabled(false);
    removeStudent.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            removeStudentActionPerformed(evt);
          }
        });

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel6Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addComponent(removeStudent)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addStudent)
                    .addContainerGap()));
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel6Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jPanel6Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(addStudent)
                            .addComponent(removeStudent))
                    .addContainerGap()));

    jPanel2.add(jPanel6, java.awt.BorderLayout.PAGE_START);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
    gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.6;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
    jPanel3.add(jPanel2, gridBagConstraints);

    add(jPanel3, java.awt.BorderLayout.CENTER);
  } // </editor-fold>//GEN-END:initComponents

  private void formAncestorRemoved(
      javax.swing.event.AncestorEvent evt) { // GEN-FIRST:event_formAncestorRemoved
  } // GEN-LAST:event_formAncestorRemoved

  private void studentTableFocusLost(
      java.awt.event.FocusEvent evt) { // GEN-FIRST:event_studentTableFocusLost
    currentSelectMssv = null;
    removeStudent.setEnabled(false);
  } // GEN-LAST:event_studentTableFocusLost

  private void removeStudentActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_removeStudentActionPerformed
    if (currentSelectMssv != null) {
      Optional<Student> student = StudentService.findByMSSV(currentSelectMssv);
      if (student.isPresent()) {
        currentCourse.getStudents().remove(student.get());
        ScheduleService.instance.update(currentCourse);
        currentSelectMssv = null;
        removeStudent.setEnabled(false);
      }
    }
  } // GEN-LAST:event_removeStudentActionPerformed

  private void nameTextActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_nameTextActionPerformed
    // TODO add your handling code here:
  } // GEN-LAST:event_nameTextActionPerformed

  private void saveBtnActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_saveBtnActionPerformed
    // TODO add your handling code here:
  } // GEN-LAST:event_saveBtnActionPerformed

  private void formAncestorAdded(
      javax.swing.event.AncestorEvent evt) { // GEN-FIRST:event_formAncestorAdded
    loadData();
    LinkedHashMap<String, String> cols = new LinkedHashMap<>();
    cols.put(Messages.t("student.mssv"), "mssv");
    cols.put(Messages.t("student.name"), "name");
    studentTable.setColumns(cols);
  } // GEN-LAST:event_formAncestorAdded

  private void classListMouseClicked(
      java.awt.event.MouseEvent evt) { // GEN-FIRST:event_classListMouseClicked
    String classCode = classList.getSelectedValue();
    loadStudent(classCode);
  } // GEN-LAST:event_classListMouseClicked

  private void addClassActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_addClassActionPerformed
    Popup.on(this).info("Not implement yet");
  } // GEN-LAST:event_addClassActionPerformed

  private void addStudentActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_addStudentActionPerformed
    AddStudent addStudentDialog =
        new AddStudent((Frame) SwingUtilities.getWindowAncestor(this), true);
    addStudentDialog.setAddHandler(this::addStudent);
    addStudentDialog.setVisible(true);
  } // GEN-LAST:event_addStudentActionPerformed

  void addStudent(@NotNull Student student) {
    if (currentCourse != null) {
      currentCourse.getStudents().add(student);
      currentCourse = ScheduleService.instance.update(currentCourse);
      studentTable.addData(student);
    }
  }

  void loadData() {
    if (courseCode != null) {
      loadCourse();
      Observable.fromCallable(() -> ScheduleService.instance.findByCourse(courseCode))
          .subscribeOn(Schedulers.io())
          .observeOn(Schedulers.single())
          .flatMapIterable(list -> list)
          .map(
              schedule -> {
                System.out.println(schedule.getClassCode());
                return schedule.getClassCode();
              })
          .toList()
          .subscribe(this::mapClassToList, this::showError);
    }
  }

  void loadCourse() {
    Course course = CourseService.getById(courseCode);
    nameText.setText(course.getName());
  }

  void loadStudent(String classCode) {
    Observable.fromCallable(() -> ScheduleService.instance.findByClassCourse(classCode, courseCode))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .map(
            schedule -> {
              currentCourse = schedule;
              return new ArrayList<>(schedule.getStudents());
            })
        .subscribe(studentTable::setData, this::showError);
  }

  void mapClassToList(List<String> classCodes) {
    DefaultListModel<String> model = new DefaultListModel<>();
    for (String code : classCodes) {
      model.addElement(code);
    }
    classList.setModel(model);
  }

  void showError(Throwable t) {
    Popup.on(this).error(t);
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addClass;
  private javax.swing.JButton addStudent;
  private javax.swing.JList<String> classList;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextField nameText;
  private javax.swing.JButton removeClass;
  private javax.swing.JButton removeStudent;
  private javax.swing.JButton saveBtn;
  private com.beohoang98.qlhs.ui.components.DataTable studentTable;
  // End of variables declaration//GEN-END:variables
}
