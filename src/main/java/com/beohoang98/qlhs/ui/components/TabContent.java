package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.ui.tabs.AvailableTabs;
import com.beohoang98.qlhs.ui.tabs.ClassDetails;
import com.beohoang98.qlhs.ui.tabs.ClassList;
import com.beohoang98.qlhs.ui.tabs.CourseDetails;
import com.beohoang98.qlhs.ui.tabs.CourseList;
import com.beohoang98.qlhs.ui.tabs.StudentMain;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import io.reactivex.rxjava3.disposables.Disposable;

public class TabContent extends JTabbedPane implements AncestorListener {
  Disposable tabListener;
  Map<AvailableTabs, JComponent> tabIndex = new HashMap<>();

  public TabContent() {
    super();
    tabListener = TabState.currentTabObserver.subscribe(this::handleChangeOrAddTab);
  }

  void handleChangeOrAddTab(@NotNull TabState.TabContext context) {
    String tabName = context.tab;
    String[] args = context.args;
    try {
      AvailableTabs tab = AvailableTabs.valueOf(tabName.toUpperCase());
      System.out.println("Selected: " + tab);
      if (tabIndex.containsKey(tab)) {
        setSelectedIndex(getComponentZOrder(tabIndex.get(tab)));
        return;
      }
      JComponent component = new JLabel("Empty", SwingConstants.CENTER);

      switch (tab) {
        case STUDENT:
          component = new StudentMain();
          break;
        case CLASS:
          component = new ClassList();
          break;
        case CLASS_DETAILS:
          component = new ClassDetails(args[0]);
          break;
        case COURSE:
          component = new CourseList();
          break;
        case CLASS_COURSE:
          component = new CourseDetails(args[0]);
          break;
        default:
          break;
      }

      tabIndex.put(tab, component);
      addTab(tabName, component);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void ancestorAdded(AncestorEvent ancestorEvent) {}

  @Override
  public void ancestorRemoved(AncestorEvent ancestorEvent) {
    if (!tabListener.isDisposed()) {
      tabListener.dispose();
    }
  }

  @Override
  public void ancestorMoved(AncestorEvent ancestorEvent) {}
}
