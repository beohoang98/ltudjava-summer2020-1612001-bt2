package com.beohoang98.qlhs.ui.state;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.Set;
import java.util.TreeSet;

public class TabState {
  public static final Subject<TabContext> currentTabObserver = PublishSubject.create();
  static Set<String> tabNames = new TreeSet<>();
  static String currentTab;

  public static void setCurrentTab(String name, String... args) {
    currentTab = name;
    currentTabObserver.onNext(new TabContext(name, args));
  }

  public static void addTab(String name, String... args) {
    tabNames.add(name);
    setCurrentTab(name, args);
  }

  public static void removeTab(String name) {
    if (tabNames.remove(name)) {
      if (tabNames.size() > 0) {
        setCurrentTab(tabNames.iterator().next());
      }
    }
  }

  public static class TabContext {
    public String tab;
    public String[] args;

    public TabContext(String tab, String... args) {
      this.tab = tab;
      this.args = args;
    }
  }
}
