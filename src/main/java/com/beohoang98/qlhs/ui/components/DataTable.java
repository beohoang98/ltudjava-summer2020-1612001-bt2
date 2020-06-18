package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;

import org.apache.commons.beanutils.PropertyUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class DataTable<T> extends JScrollPane {
  private final Map<String, String> columns;
  private final Class<T> tClass;
  private JTable table;
  private boolean isLoading = false;
  private JLabel loadingLabel;

  public DataTable(Class<T> tClass, @NotNull Map<String, String> columns) {
    super();
    this.columns = columns;
    this.tClass = tClass;
    initComponents();
  }

  private void initComponents() {
    table = new JTable();
    table.setFillsViewportHeight(true);
    table.setAutoCreateRowSorter(true);
    loadingLabel =
        new JLabel(
            Messages.Others.LOADING, new ImageIcon("ajax-loader.gif"),
            SwingConstants.CENTER);
    setViewportView(table);
  }

  public void setData(@NotNull List<T> data) {
    DefaultTableModel model =
        new DefaultTableModel(columns.keySet().toArray(), 0) {
          @Override
          public boolean isCellEditable(int i, int i1) {
            return false;
          }
        };
    for (T item : data) {
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
}
