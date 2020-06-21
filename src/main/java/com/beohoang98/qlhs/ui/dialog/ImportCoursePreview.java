/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beohoang98.qlhs.ui.dialog;

import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.mapping.CourseDto;
import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.services.ScheduleService;
import com.beohoang98.qlhs.services.SchoolClassService;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.utils.Popup;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** @author noobcoder */
public class ImportCoursePreview extends javax.swing.JDialog {

  private List<CourseDto> courseDtos;
  private boolean isClassLoaded = false;

  /** Creates new form ImportCoursePreview */
  public ImportCoursePreview(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    dataTable = new com.beohoang98.qlhs.ui.components.DataTable();
    jLabel1 = new javax.swing.JLabel();
    comboBoxClass = new javax.swing.JComboBox<>();
    addBtn = new javax.swing.JButton();
    fileSelectBtn = new javax.swing.JButton();
    filenameLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    addContainerListener(
        new java.awt.event.ContainerAdapter() {
          public void componentAdded(java.awt.event.ContainerEvent evt) {
            formComponentAdded(evt);
          }
        });
    addHierarchyListener(
        new java.awt.event.HierarchyListener() {
          public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
            formHierarchyChanged(evt);
          }
        });
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowActivated(java.awt.event.WindowEvent evt) {
            formWindowActivated(evt);
          }
        });

    jLabel1.setText("Chọn lớp");

    comboBoxClass.setModel(new javax.swing.DefaultComboBoxModel<>());
    comboBoxClass.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            comboBoxClassActionPerformed(evt);
          }
        });

    addBtn.setText("Thêm");
    addBtn.setEnabled(false);
    addBtn.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            addBtnActionPerformed(evt);
          }
        });

    fileSelectBtn.setText("Chọn file ...");
    fileSelectBtn.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            fileSelectBtnActionPerformed(evt);
          }
        });

    filenameLabel.setText("...");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(
                                dataTable,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(fileSelectBtn)
                                    .addGap(18, 18, 18)
                                    .addComponent(
                                        filenameLabel,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        148,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        188,
                                        Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(
                                        comboBoxClass,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        150,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(addBtn)))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(
                                comboBoxClass,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fileSelectBtn)
                            .addComponent(filenameLabel))
                    .addGap(28, 28, 28)
                    .addComponent(
                        dataTable, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(addBtn)
                    .addContainerGap()));

    pack();
  } // </editor-fold>//GEN-END:initComponents

  private void addBtnActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_addBtnActionPerformed
    URL iconUrl = getClass().getClassLoader().getResource("ajax-loader.gif");
    addBtn.setIcon(new ImageIcon(iconUrl));
    addBtn.setEnabled(false);

    Single.fromCallable(
            () -> {
              ScheduleService.instance.importFrom(
                  courseDtos, (String) comboBoxClass.getSelectedItem());
              return 0;
            })
        .doFinally(
            () -> {
              addBtn.setEnabled(true);
              addBtn.setIcon(null);
            })
        .subscribe(
            (n) -> {
              dispose();
            },
            this::showError);
  } // GEN-LAST:event_addBtnActionPerformed

  private void formComponentAdded(
      java.awt.event.ContainerEvent evt) { // GEN-FIRST:event_formComponentAdded
  } // GEN-LAST:event_formComponentAdded

  void mapClassToComboxBox(List<SchoolClass> classes) {
    classes.forEach(
        c -> {
          comboBoxClass.addItem(c.getCode());
        });
    isClassLoaded = true;
  }

  void showError(Throwable t) {
    Popup.on(rootPane).error(t);
  }

  private void comboBoxClassActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_comboBoxClassActionPerformed
    // TODO add your handling code here:
  } // GEN-LAST:event_comboBoxClassActionPerformed

  private void fileSelectBtnActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_fileSelectBtnActionPerformed
    final JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      filenameLabel.setText(file.getName());
      try {
        courseDtos = CourseService.importFromCsv(file);
        mapCoursesToTable();
      } catch (Exception e) {
        this.showError(e);
      }
    }
  } // GEN-LAST:event_fileSelectBtnActionPerformed

  private void formHierarchyChanged(
      java.awt.event.HierarchyEvent evt) { // GEN-FIRST:event_formHierarchyChanged
    // TODO add your handling code here:

  } // GEN-LAST:event_formHierarchyChanged

  private void formWindowActivated(
      java.awt.event.WindowEvent evt) { // GEN-FIRST:event_formWindowActivated
    comboBoxClass.removeAllItems();
    Single.fromCallable(() -> SchoolClassService.findAll(false))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(this::mapClassToComboxBox, this::showError);
  } // GEN-LAST:event_formWindowActivated

  void mapCoursesToTable() {
    dataTable.setLoading(true);
    LinkedHashMap<String, String> cols = new LinkedHashMap<>();
    cols.put(Messages.t("course.code"), "code");
    cols.put(Messages.t("course.name"), "name");
    cols.put(Messages.t("course.room"), "room");
    dataTable.setColumns(cols);
    dataTable.setData(courseDtos);
    dataTable.setLoading(false);
    addBtn.setEnabled(true);
  }

  /** @param args the command line arguments */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info :
          javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(ImportCoursePreview.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(ImportCoursePreview.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(ImportCoursePreview.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ImportCoursePreview.class.getName())
          .log(java.util.logging.Level.SEVERE, null, ex);
    }
    // </editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            ImportCoursePreview dialog = new ImportCoursePreview(new javax.swing.JFrame(), true);
            dialog.addWindowListener(
                new java.awt.event.WindowAdapter() {
                  @Override
                  public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                  }
                });
            dialog.setVisible(true);
          }
        });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addBtn;
  private javax.swing.JComboBox<String> comboBoxClass;
  private com.beohoang98.qlhs.ui.components.DataTable dataTable;
  private javax.swing.JButton fileSelectBtn;
  private javax.swing.JLabel filenameLabel;
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables
}