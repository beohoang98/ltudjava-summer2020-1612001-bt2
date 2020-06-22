package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataTable extends JScrollPane {

  private Map<String, String> columns;

  public final Subject<Object[]> currentSelection;
  private JTable table;
  private boolean isLoading = false;
  private JLabel loadingLabel;

  List<SelectHandler> selectHandlers = new ArrayList<>();

  public DataTable() {
    super();
    this.currentSelection = ReplaySubject.create();
    columns = new LinkedHashMap<>();
    initComponents();
  }

  public DataTable(@NotNull Map<String, String> columns) {
    super();
    this.currentSelection = ReplaySubject.create();
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
          public void mouseClicked(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {
              onSelect(me);
            }
            ;
          }
        });
    table.addFocusListener(
        new FocusAdapter() {
          @Override
          public void focusLost(FocusEvent fe) {
            onSelect(null);
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
                  PropertyUtilsBean pub = new PropertyUtilsBean();
                  cells.add(pub.getProperty(item, column));
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

  public void removeData(int index) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.removeRow(index);
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

  void onSelect(@Nullable MouseEvent mouseEvent) {
    if (mouseEvent == null) {
      currentSelection.onNext(Arrays.asList().toArray());
      return;
    }
    int rowIdx = table.rowAtPoint(mouseEvent.getPoint());
    if (rowIdx >= 0) {
      List<Object> cells = new ArrayList<>();
      for (int cellIdx = 0; cellIdx < columns.size(); ++cellIdx) {
        cells.add(table.getValueAt(rowIdx, cellIdx));
      }
      currentSelection.onNext(cells.toArray());
      for (SelectHandler handler : selectHandlers) {
        handler.onSelect(cells.toArray());
      }
    }
  }

  public void addSelectHandler(SelectHandler handler) {
    selectHandlers.add(handler);
  }

  public interface SelectHandler {

    void onSelect(Object[] cells);
  }
}
