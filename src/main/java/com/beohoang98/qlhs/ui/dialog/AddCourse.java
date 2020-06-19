package com.beohoang98.qlhs.ui.dialog;

import com.beohoang98.qlhs.entities.Course;
import com.beohoang98.qlhs.services.CourseService;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.styles.AppColor;
import com.beohoang98.qlhs.ui.styles.Margin;

import org.jetbrains.annotations.NotNull;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCourse extends JDialog {
  Container contentPane;
  JTextField code = new JTextField();
  JTextField name = new JTextField();
  JPanel body = new JPanel();
  JPanel footer = new JPanel();
  JLabel loadingPanel;

  AddHandler addHandler;

  public AddCourse(JComponent parent) {
    super();
    setLocationRelativeTo(parent);
    initComponents();
    pack();
  }

  /**
   * DEBUG ONLY
   *
   * @param args
   */
  public static void main(String[] args) throws InterruptedException {
    AddCourse dialog = new AddCourse(null);
    dialog.setVisible(true);
    dialog.showLoading();
    Thread.sleep(1000);
    dialog.hideLoading();
  }

  private void initComponents() {
    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    setPreferredSize(new Dimension(300, 200));
    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent windowEvent) {
            onCancel();
          }
        });

    loadingPanel = new JLabel(Messages.t("loading"), SwingConstants.CENTER);

    body = new JPanel();

    contentPane.add(body, BorderLayout.CENTER);
    //    contentPane.add(loadingPanel, BorderLayout.CENTER);
    contentPane.add(footer, BorderLayout.SOUTH);

    body.setLayout(new GridBagLayout());
    body.setBorder(BorderFactory.createEmptyBorder(Margin.x2, Margin.x2, Margin.x2, Margin.x2));
    footer.setLayout(new FlowLayout(FlowLayout.RIGHT));

    GridBagConstraints gbcLabel = new GridBagConstraints();
    gbcLabel.fill = GridBagConstraints.HORIZONTAL;
    gbcLabel.gridx = 0;
    gbcLabel.gridy = 0;
    gbcLabel.weightx = 0.3;

    GridBagConstraints gbcField = new GridBagConstraints();
    gbcField.fill = GridBagConstraints.HORIZONTAL;
    gbcField.gridx = 1;
    gbcField.gridy = 0;
    gbcField.weightx = 0.7;

    body.add(new JLabel(Messages.t("course.code")), gbcLabel);
    gbcLabel.gridy = 1;
    body.add(new JLabel(Messages.t("course.name")), gbcLabel);

    body.add(code, gbcField);
    gbcField.gridy = 1;
    body.add(name, gbcField);

    JButton addBtn = new JButton(Messages.t("button.add"));
    addBtn.addActionListener(actionEvent -> onAdd());
    addBtn.setBackground(AppColor.PRIMARY);
    addBtn.setForeground(AppColor.SIDEBAR_FG);

    JButton cancelBtn = new JButton(Messages.t("button.cancel"));
    cancelBtn.addActionListener(actionEvent -> onCancel());
    footer.add(addBtn);
    footer.add(cancelBtn);
  }

  void onAdd() {
    String codeValue = code.getText();
    String nameValue = name.getText();
    showLoading();

    Single.fromCallable(() -> CourseService.add(codeValue, nameValue))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .doFinally(this::hideLoading)
        .subscribe(this::onFinish, this::showError);
  }

  void onFinish(Course course) {
    if (addHandler != null) {
      addHandler.onAdded(course);
    }
    onCancel();
  }

  void showError(@NotNull Throwable throwable) {
    throwable.printStackTrace();
    JOptionPane.showMessageDialog(
        this, throwable, throwable.getMessage(), JOptionPane.ERROR_MESSAGE);
  }

  void onCancel() {
    dispose();
  }

  public void showLoading() {
    contentPane.remove(footer);
    contentPane.add(loadingPanel, BorderLayout.SOUTH);
    contentPane.revalidate();
    contentPane.repaint();
    System.out.print("Loading...");
  }

  public void hideLoading() {
    contentPane.remove(loadingPanel);
    contentPane.add(footer, BorderLayout.SOUTH);
    System.out.printf("\r%10s\n", "");
    contentPane.revalidate();
    contentPane.repaint();
  }

  public void setAddHandler(AddHandler addHandler) {
    this.addHandler = addHandler;
  }

  public interface AddHandler {
    void onAdded(Course course);
  }
}
