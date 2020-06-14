package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Sidebar extends JPanel implements ActionListener {
  JButton studentBtn = new JButton(Messages.Objects.STUDENT);
  JButton classBtn = new JButton(Messages.Objects.CLASS);
  JButton courseBtn = new JButton(Messages.Objects.COURSES);

  public Sidebar() {
    super();
    makeUI();
  }

  void makeUI() {
    setBorder(BorderFactory.createEmptyBorder());
    setLayout(new GridLayout(0, 1));

    studentBtn.setActionCommand("open:student");
    studentBtn.addActionListener(this);
    classBtn.setActionCommand("open:class");
    classBtn.addActionListener(this);
    courseBtn.setActionCommand("open:course");
    courseBtn.addActionListener(this);

    add(studentBtn);
    add(classBtn);
    add(courseBtn);
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String command = actionEvent.getActionCommand();
    if (command.startsWith("open:")) {
      String tabName = command.replaceAll("open:", "");
      TabState.addTab(tabName);
    }
  }
}
