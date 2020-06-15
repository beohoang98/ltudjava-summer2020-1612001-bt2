package com.beohoang98.qlhs.ui.tabs;

import com.beohoang98.qlhs.entities.SchoolClass;
import com.beohoang98.qlhs.services.SchoolClassService;
import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.ui.styles.Margin;

import org.jetbrains.annotations.NotNull;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClassList extends JPanel implements AncestorListener {
  JPanel loadingLabel = new JPanel();
  JPanel formControl = new JPanel();
  JScrollPane tableWrapper = new JScrollPane();
  JTable table = new JTable();
  Disposable disposable;

  public ClassList() {
    super();
    makeUI();
    loadData();
  }

  void makeUI() {
    setBorder(BorderFactory.createEmptyBorder());
    setLayout(new BorderLayout());
    add(formControl, BorderLayout.NORTH);
    add(tableWrapper, BorderLayout.CENTER);
    table.setFillsViewportHeight(true);
    table.setAutoCreateRowSorter(true);

    JLabel filterLabel = new JLabel("Filter"); // TODO: handle messages
    JTextField filterNameField = new JTextField();
    formControl.setLayout(new FlowLayout(FlowLayout.LEFT, Margin.x1, Margin.x1));
    formControl.add(filterLabel);
    formControl.add(filterNameField);
  }

  public void loadData() {
    showLoading();
    disposable =
        Flowable.fromCallable(() -> SchoolClassService.findAll(true))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .doFinally(this::hideLoading)
            .subscribe(this::fillDataToTable, this::showError);
  }

  void fillDataToTable(@NotNull List<SchoolClass> classes) {
    List<String> columns =
        Arrays.asList("STT", Messages.SchoolClass.CODE, Messages.SchoolClass.STUDENT_NUMBER);
    DefaultTableModel model =
        new DefaultTableModel(columns.toArray(), 0) {
          @Override
          public boolean isCellEditable(int i, int i1) {
            return false;
          }
        };
    table.setModel(model);
    table.addMouseListener(new RowClickHandler());

    for (int stt = 1, len = classes.size(); stt <= len; ++stt) {
      SchoolClass schoolClass = classes.get(stt - 1);
      List<?> cells = Arrays.asList(stt, schoolClass.getCode(), schoolClass.getCount());
      model.addRow(cells.toArray());
    }
  }

  void showError(@NotNull Throwable throwable) {
    JOptionPane.showMessageDialog(
        this,
        new JTextArea(throwable.getLocalizedMessage()),
        throwable.getMessage(),
        JOptionPane.ERROR_MESSAGE);
  }

  void showLoading() {
    tableWrapper.setViewportView(loadingLabel);
  }

  void hideLoading() {
    tableWrapper.setViewportView(table);
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

  public class RowClickHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
      int rowIndex = table.rowAtPoint(mouseEvent.getPoint());
      if (rowIndex < 0) return;
      String selectedId = (String) table.getModel().getValueAt(rowIndex, 1);
      System.out.println("Select class " + selectedId);
      TabState.addTab("class_details", selectedId);
    }
  }
}
