package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.ui.tabs.AvailableTabs;
import com.beohoang98.qlhs.ui.tabs.ClassDetails;
import com.beohoang98.qlhs.ui.tabs.ClassList;
import com.beohoang98.qlhs.ui.tabs.StudentMain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import io.reactivex.rxjava3.disposables.Disposable;

public class TabContent extends JTabbedPane implements AncestorListener {
  Disposable tabListener;
  List<AvailableTabs> tabIndex = new ArrayList<>();

  public TabContent() {
    super();
    tabListener = TabState.currentTabObserver.subscribe(this::handleChangeOrAddTab);
  }

  void handleChangeOrAddTab(TabState.TabContext context) {
    String tabName = context.tab;
    String[] args = context.args;
    try {
      AvailableTabs tab = AvailableTabs.valueOf(tabName.toUpperCase());
      System.out.println("Selected: " + tab);
      if (tabIndex.contains(tab)) {
        setSelectedIndex(tabIndex.indexOf(tab));
        return;
      }
      tabIndex.add(tab);
      //      setSelectedIndex(tabIndex.indexOf(tab));

      switch (tab) {
        case STUDENT:
          addTab(tabName, new StudentMain());
          break;
        case CLASS:
          {
            ClassList classListTab = new ClassList();
            addTab(tabName, classListTab);
            break;
          }
        case CLASS_DETAILS:
          {
            addTab(tabName, new ClassDetails(args[0]));
            break;
          }
        case COURSE:
        default:
          break;
      }
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
