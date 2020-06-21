package com.beohoang98.qlhs.ui.dialog;

import com.beohoang98.qlhs.mapping.StudentDto;
import com.beohoang98.qlhs.services.SchoolClassService;
import com.beohoang98.qlhs.services.StudentService;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.styles.Margin;
import com.opencsv.exceptions.CsvException;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ImportPreview extends JFrame implements ActionListener {
  File file;
  List<StudentDto> studentDtoList;

  JTable table = new JTable();
  JTextField schoolClassField = new JTextField();
  JCheckBox isOverrideCheckbox = new JCheckBox();
  ImportSuccessHandler importSuccessHandler;

  public ImportPreview(File file) throws FileNotFoundException, CsvException {
    super();
    this.file = file;
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle(Messages.Others.IMPORT_FORM_TITLE);
    makeUI();
    fillTable();
    pack();
  }

  void makeUI() {
    Container container = getContentPane();
    JScrollPane scrollPane = new JScrollPane(table);
    table.setFillsViewportHeight(true);
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

    JPanel formPanel = makeForm();

    JPanel actionPane = new JPanel();
    actionPane.setLayout(new FlowLayout());
    JButton cancelButton = new JButton(Messages.Button.CANCEL);
    cancelButton.setActionCommand("close");
    cancelButton.addActionListener(this);
    JButton okButton = new JButton(Messages.Button.IMPORT);
    okButton.setActionCommand("ok");
    okButton.addActionListener(this);
    actionPane.add(cancelButton);
    actionPane.add(okButton);

    container.add(scrollPane);
    container.add(formPanel);
    container.add(actionPane);
  }

  JPanel makeForm() {
    JPanel formPanel = new JPanel();
    formPanel.setBorder(
        BorderFactory.createEmptyBorder(Margin.x1, Margin.x1, Margin.x1, Margin.x1));
    formPanel.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.BOTH;
    constraints.insets = new Insets(Margin.x1, Margin.x1, Margin.x1, Margin.x1);

    constraints.weightx = 0.2f;
    constraints.gridx = 0;
    constraints.gridy = 0;
    formPanel.add(new JLabel(Messages.SchoolClass.CODE, SwingConstants.RIGHT), constraints);

    constraints.gridy = 1;
    formPanel.add(
        new JLabel(Messages.Others.OVERWRITE_VALUE_IF_EXIST, SwingConstants.RIGHT), constraints);

    constraints.gridx = 1;
    constraints.weightx = 0.8f;
    constraints.gridy = 0;
    formPanel.add(schoolClassField, constraints);

    constraints.gridy = 1;
    formPanel.add(isOverrideCheckbox, constraints);

    return formPanel;
  }

  void fillTable() throws FileNotFoundException, CsvException {
    FileReader reader = new FileReader(file);
    studentDtoList = StudentService.importFromFile(reader);

    String[] columns = {"mssv", "ho ten", "gioi tinh", "cmnd"};
    DefaultTableModel model =
        new DefaultTableModel(columns, 0) {
          @Override
          public boolean isCellEditable(int i, int i1) {
            return false;
          }
        };
    table.setModel(model);

    for (StudentDto dto : studentDtoList) {
      List<Object> cells = Arrays.asList(dto.mssv, dto.name, dto.gender, dto.cmnd);
      model.addRow(cells.toArray());
    }
  }

  public void setImportSuccessHandler(ImportSuccessHandler importSuccessHandler) {
    this.importSuccessHandler = importSuccessHandler;
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String command = actionEvent.getActionCommand();
    if (command.equals("close")) {
      dispose();
    } else if (command.equals("ok")) {
      handleImport();
    }
  }

  void handleImport() {
    String schoolCode = schoolClassField.getText();
    if (schoolCode == null || schoolCode.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this, Messages.Errors.SCHOOL_CLASS_EMPTY, "", JOptionPane.ERROR_MESSAGE);
      return;
    }
    SchoolClassService.addStudents(schoolCode, studentDtoList);
    JOptionPane.showMessageDialog(
        this, Messages.Success.IMPORT_SUCCESS, "", JOptionPane.INFORMATION_MESSAGE);
    if (importSuccessHandler != null) {
      importSuccessHandler.onSuccess();
    }
  }

  public static interface ImportSuccessHandler {
    void onSuccess();
  }
}
