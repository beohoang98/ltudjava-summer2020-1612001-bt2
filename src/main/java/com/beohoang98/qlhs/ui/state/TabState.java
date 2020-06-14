package com.beohoang98.qlhs.ui.state;

import java.util.Set;
import java.util.TreeSet;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class TabState {
  public static final Subject<String> currentTabObserver = PublishSubject.create();
  static Set<String> tabNames = new TreeSet<>();
  static String currentTab;

  public static void setCurrentTab(String name) {
    currentTab = name;
    currentTabObserver.onNext(name);
  }

  public static void addTab(String name) {
    tabNames.add(name);
    setCurrentTab(name);
  }

  public static void removeTab(String name) {
    if (tabNames.remove(name)) {
      if (tabNames.size() > 0) {
        setCurrentTab(tabNames.iterator().next());
      }
    }
  }
}
