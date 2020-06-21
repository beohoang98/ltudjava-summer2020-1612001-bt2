package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.beanutils.PropertyUtils;
import org.jetbrains.annotations.NotNull;

public class DataTable extends JScrollPane {

  private Map<String, String> columns;
  public final PublishSubject<Object[]> currentSelection = PublishSubject.create();
  private JTable table;
  private boolean isLoading = false;
  private JLabel loadingLabel;

  public DataTable() {
    super();
    columns = new LinkedHashMap<>();
    initComponents();
  }

  public DataTable(@NotNull Map<String, String> columns) {
    super();
    this.columns = columns;
    initComponents();
  }

  private void initComponents() {
    table = new JTable();
    table.setFillsViewportHeight(true);
    table.setAutoCreateRowSorter(true);
    table.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent mouseEvent) {
            onSelect(mouseEvent);
          }
        });

    ImageIcon icon =
        new ImageIcon(
            Objects.requireNonNull(getClass().getClassLoader().getResource("ajax-loader.gif")));
    loadingLabel = new JLabel(Messages.t("loading"), icon, SwingConstants.CENTER);
    loadingLabel.setForeground(Color.BLACK);
    setViewportView(table);
  }

  public void setColumns(LinkedHashMap<String, String> cols) {
    columns = cols;
  }

  public void setData(@NotNull List<?> data) {
    DefaultTableModel model =
        new DefaultTableModel(columns.keySet().toArray(), 0) {
          @Override
          public boolean isCellEditable(int i, int i1) {
            return false;
          }
        };
    for (Object item : data) {
      List<Object> cells = new ArrayList<>();
      columns
          .values()
          .forEach(
              column -> {
                try {
                  Map<String, Object> props = PropertyUtils.describe(item);
                  cells.add(props.get(column));
                } catch (IllegalAccessException noSuchFieldException) {
                  noSuchFieldException.printStackTrace();
                  System.out.print(item);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                  e.printStackTrace();
                }
              });
      model.addRow(cells.toArray());
    }
    this.table.setModel(model);
  }

  public void addData(Object item) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    List<Object> cells = new ArrayList<>();
    columns
        .values()
        .forEach(
            column -> {
              try {
                Map<String, Object> props = PropertyUtils.describe(item);
                cells.add(props.get(column));
              } catch (IllegalAccessException noSuchFieldException) {
                noSuchFieldException.printStackTrace();
                System.out.print(item);
              } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
              }
            });
    model.addRow(cells.toArray());
  }

  public boolean isLoading() {
    return isLoading;
  }

  public void setLoading(boolean isLoading) {
    this.isLoading = isLoading;
    if (isLoading) {
      setViewportView(loadingLabel);
    } else {
      setViewportView(table);
    }
  }

  void onSelect(@NotNull MouseEvent mouseEvent) {
    int rowIdx = table.rowAtPoint(mouseEvent.getPoint());
    if (rowIdx >= 0) {
      List<Object> cells = new ArrayList<>();
      for (int cellIdx = 0; cellIdx < columns.size(); ++cellIdx) {
        cells.add(table.getValueAt(rowIdx, cellIdx));
      }
      currentSelection.onNext(cells.toArray());
    }
  }
}
