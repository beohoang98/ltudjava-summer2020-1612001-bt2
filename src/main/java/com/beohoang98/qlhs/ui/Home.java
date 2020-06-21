package com.beohoang98.qlhs.ui;

import com.beohoang98.qlhs.ui.components.Sidebar;
import com.beohoang98.qlhs.ui.components.TabContent;
import com.beohoang98.qlhs.ui.dialog.ImportCourseFromCSVDialog;
import com.beohoang98.qlhs.ui.dialog.ImportPreview;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.styles.AppColor;
import com.opencsv.exceptions.CsvException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Home extends JFrame implements ActionListener, ItemListener {
  public static Logger logger = LogManager.getLogger();
  public static String title = "Home";

  private final Sidebar sidebar = new Sidebar();
  private final JPanel content = new JPanel();
  private final JMenuBar menuBar = new JMenuBar();

  public Home() {
    super(title);
    setPreferredSize(new Dimension(1280, 720));
    makeUI();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    pack();
    setVisible(true);
  }

  /** DEVELOPMENT ONLY */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(new MaterialLookAndFeel());
      MaterialLookAndFeel.changeTheme(new JMarsDarkTheme());
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    new Home();
  }

  void makeUI() {
    Container container = getContentPane();
    container.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();

    makeMenu();

    sidebar.setBackground(AppColor.SIDEBAR_BG);
    sidebar.setForeground(AppColor.SIDEBAR_FG);
    sidebar.setMaximumSize(new Dimension(150, sidebar.getPreferredSize().height));

    content.setLayout(new BorderLayout());
    content.setBorder(BorderFactory.createEmptyBorder());
    content.add(new TabContent(), BorderLayout.CENTER);

    constraints.anchor = GridBagConstraints.NORTH;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 1;
    constraints.gridwidth = 2;
    constraints.fill = GridBagConstraints.BOTH;
    container.add(menuBar, constraints);

    constraints.gridwidth = 1;
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.weightx = 0.2;
    constraints.weighty = 1;
    container.add(sidebar, constraints);

    constraints.gridx = 1;
    constraints.weightx = 0.8;
    container.add(content, constraints);
  }

  void makeMenu() {
    JMenu main = new JMenu("Main");
    JMenuItem mainExit = new JMenuItem("Exit");
    mainExit.setActionCommand("exit");
    mainExit.addActionListener(this);
    JMenuItem mainLogOut = new JMenuItem("Log Out");
    mainLogOut.setActionCommand("logOut");
    mainLogOut.addActionListener(this);
    main.add(mainLogOut);
    main.addSeparator();
    main.add(mainExit);

    JMenu importMenu = new JMenu(Messages.t("menu.import"));
    JMenuItem studentItem = new JMenuItem(Messages.t("menu.import.students"));
    studentItem.setActionCommand("student:import");
    studentItem.addActionListener(this);
    JMenuItem courseItem = new JMenuItem(Messages.t("menu.import.courses"));
    courseItem.setActionCommand("course:import");
    courseItem.addActionListener(this);

    importMenu.add(studentItem);
    importMenu.add(courseItem);

    menuBar.add(main);
    menuBar.add(importMenu);
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String command = actionEvent.getActionCommand();
    switch (command) {
      case "exit":
        dispose();
        break;
      case "student:import":
        handleImportStudent();
        break;
      case "course:import":
        handleImportCourse();
        break;
    }
  }

  public void handleImportStudent() {
    final JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      logger.debug(file.getPath());
      try {
        ImportPreview importPreview = new ImportPreview(file);
        importPreview.setVisible(true);
      } catch (CsvException | RuntimeException csvException) {
        JOptionPane.showMessageDialog(this, Messages.Errors.CSV_WRONG_FORMAT);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  void handleImportCourse() {
    final JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      JDialog importDialog = new ImportCourseFromCSVDialog(this, file);
      importDialog.setVisible(true);
    }
  }

  @Override
  public void itemStateChanged(ItemEvent itemEvent) {}
}
