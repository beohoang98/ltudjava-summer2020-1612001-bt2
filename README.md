# ltudjava-summer2020-1612001-bt2
Student Management App written with Java Swing and [Hibernate](https://hibernate.org/orm/)

## Build & Run

- Project using [Gradle](https://gradle.org/) to manage dependencies and builds. So you need install Gradle to build this

```bash
# build and test
./gradlew build

# run only
./gradlew run
```

- Graphics (UI) development with [NetBeans](https://netbeans.org/)

## Development

1. Java 8 (version 1.8)

2. Database **PostgreSQL** or **MySQL**
    
    + One for using app
    
    + One for testing (optional)
    
3. Create `.env` (from .env.example)

    ```dotenv
    JDBC_DATABASE_URL=jdbc:postgresql://....
    JDBC_DRIVER=...
    ```
    
4. Build and run by `./gradlew :run` or run with NetBeans

## Tự chấm điểm

> mỗi mục 10đ , tổng 150đ

|   | Chức năng                                 | Điểm
|---|---|---:|
|X  | 1. Import danh sách lớp bằng CSV          | 10
|   | 2. Thêm một sinh viên vào hệ thống        | 0
|X  | 3. Import thời khóa biểu bằng CSV         | 10
|X  | 4. Giáo vụ (đăng ký theo môn)             | 10
|X  | 5. Giáo vụ (danh sách lớp)                | 10
|X  | 6. Thời khóa biểu                         | 10
|   | 7. Import bẳng điểm                       | 0
|   | 8. Xem bảng điểm, thống kê đậu rớt        | 0
|   | 9. Sửa điểm                               | 0
|   | 10. Sinh viên xem điểm                    | 0
|X  | 11. Đăng nhập / Đăng xuất                 | 10
|   | 12. Đổi mật khẩu                          | 0
|   | 13. Phúc khảo tất cả các môn              | 0
|   | 14. Xem phúc khảo của SV, cập nhật trạng thái | 0
|   | 15. Sinh viên phúc khảo                   | 0
|---|
|   |   **Total**| 60 
