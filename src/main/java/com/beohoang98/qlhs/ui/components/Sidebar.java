package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Sidebar extends JPanel implements ActionListener {
  JButton studentBtn = new JButton(Messages.Objects.STUDENT);
  JButton classBtn = new JButton(Messages.Objects.CLASS);
  JButton courseBtn = new JButton(Messages.Objects.COURSES);
  JButton reCheckBtn = new JButton(Messages.t("recheck"));

  public Sidebar() {
    super();
    makeUI();
  }

  void makeUI() {
    setBorder(BorderFactory.createEmptyBorder());
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder());

    JPanel content = new JPanel();
    content.setLayout(new GridBagLayout());
    add(content, BorderLayout.NORTH);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.weightx = 1;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.anchor = GridBagConstraints.NORTH;

    studentBtn.setActionCommand("open:student");
    studentBtn.addActionListener(this);
    classBtn.setActionCommand("open:class");
    classBtn.addActionListener(this);
    courseBtn.setActionCommand("open:course");
    courseBtn.addActionListener(this);
    reCheckBtn.setActionCommand("open:recheck");
    reCheckBtn.addActionListener(this);

    gbc.gridy = 0;
    content.add(studentBtn, gbc);

    gbc.gridy = 1;
    content.add(classBtn, gbc);

    gbc.gridy = 2;
    content.add(courseBtn, gbc);
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
