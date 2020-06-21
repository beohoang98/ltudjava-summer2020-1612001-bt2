package com.beohoang98.qlhs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AppConfig {
  static Properties properties;

  static {
    InputStream inputStream =
        AppConfig.class.getClassLoader().getResourceAsStream("app.properties");
    properties = new Properties();
    try {
      properties.load(inputStream);
      if (inputStream != null) {
        inputStream.close();
      }
    } catch (IOException ioException) {
      Logger.getGlobal().warning("Cannot load config file " + ioException.getMessage());
    }
  }

  public static Properties get() {
    return properties;
  }
}
