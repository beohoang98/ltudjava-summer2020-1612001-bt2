package com.beohoang98.qlhs.ui.tabs;

import org.jetbrains.annotations.NotNull;

public enum AvailableTabs {
  STUDENT,
  CLASS,
  CLASS_DETAILS,
  COURSE,
  COURSE_DETAILS,
  CLASS_COURSE;

  boolean equals(@NotNull String str) {
    return this.name().toLowerCase().equals(str.toLowerCase());
  }
}
