package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.ui.tabs.AvailableTabs;
import com.beohoang98.qlhs.ui.tabs.StudentMain;

import org.jetbrains.annotations.NotNull;

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

  void handleChangeOrAddTab(@NotNull String tabName) {
    try {
      AvailableTabs tab = AvailableTabs.valueOf(tabName.toUpperCase());
      if (tabIndex.contains(tab)) {
        setSelectedIndex(tabIndex.indexOf(tab));
        return;
      }
      tabIndex.add(tab);
      switch (tab) {
        case STUDENT:
          addTab(tabName, new StudentMain());
          break;
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
