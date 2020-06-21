package com.beohoang98.qlhs.ui.components;

import com.beohoang98.qlhs.ui.messages.Messages;
import com.beohoang98.qlhs.ui.state.TabState;
import com.beohoang98.qlhs.ui.styles.Margin;
import com.beohoang98.qlhs.ui.tabs.AvailableTabs;
import com.beohoang98.qlhs.ui.tabs.ClassDetails;
import com.beohoang98.qlhs.ui.tabs.ClassList;
import com.beohoang98.qlhs.ui.tabs.CourseDetails;
import com.beohoang98.qlhs.ui.tabs.CourseList;
import com.beohoang98.qlhs.ui.tabs.StudentMain;
import io.reactivex.rxjava3.disposables.Disposable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.jetbrains.annotations.NotNull;

public class TabContent extends JTabbedPane implements AncestorListener {
  Disposable tabListener;
  Map<AvailableTabs, JComponent> tabComponents = new HashMap<>();
  List<AvailableTabs> tabIndex = new ArrayList<>();

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
      if (tabComponents.containsKey(tab)) {
        setSelectedIndex(tabIndex.indexOf(tab));
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
        case COURSE_DETAILS:
            // TODO
          component = new CourseDetails(args[0]);
          break;
        default:
          break;
      }

      tabComponents.put(tab, component);
      tabIndex.add(tab);
      addTab(tabName, component);

      int index = tabIndex.size() - 1;
      setTabComponentAt(index, new ClosableButton(tabName, tab));
      setSelectedIndex(index);
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

  public class ClosableButton extends JPanel {
    public ClosableButton(String title, AvailableTabs tab) {
      super();
      JLabel label = new JLabel(Messages.t("tab." + title), CENTER);
      add(label, BorderLayout.CENTER);

      Icon icon = UIManager.getIcon("InternalFrame.closeIcon");
      JButton closeBtn = new JButton(icon);
      closeBtn.setPreferredSize(new Dimension(Margin.x1, Margin.x1));
      add(closeBtn, BorderLayout.LINE_END);

      closeBtn.addActionListener(
          actionEvent -> {
            removeTabAt(tabIndex.indexOf(tab));
            tabIndex.remove(tab);
            tabComponents.remove(tab);
          });
    }
  }
}
