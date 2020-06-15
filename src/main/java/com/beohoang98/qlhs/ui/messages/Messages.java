package com.beohoang98.qlhs.ui.messages;

public class Messages {
  public static class Button {
    public static String CANCEL = "Hủy";
    public static String OK = "Đồng ý";
    public static String IMPORT = "Import";
  }

  public static class Objects {
    public static String STUDENT = "Sinh viên";
    public static String CLASS = "Lớp học";
    public static String COURSES = "Môn học";
  }

  public static class SchoolClass {
    public static String CODE = "Mã lớp";
    public static String STUDENT_NUMBER = "Số sinh viên";
  }

  public static class Student {
    public static String MSSV = "MSSV";
    public static String NAME = "Họ và tên";
    public static String GENDER = "Giới tính";
    public static String CMND = "CMND";
    public static String GK = "Điểm giữa kì";
    public static String CK = "Điểm cuối kì";
    public static String AVERAGE_PTS = "Điểm trung bình";
  }

  public static class Errors {
    public static String CSV_WRONG_FORMAT = "File csv format không đúng chuẩn";
    public static String SCHOOL_CLASS_EMPTY = "Bạn chưa nhập mã lớp";
  }

  public static class Success {
    public static String IMPORT_SUCCESS = "Import thành công";
  }

  public static class Others {
    public static String OVERWRITE_VALUE_IF_EXIST = "Ghi đè dữ liệu (nếu có)";
    public static String IMPORT_FORM_TITLE = "Import dữ liệu";
  }
}
