# ltudjava-summer2020-1612001-bt2
Student Management App written with Java Swing and [Hibernate](https://hibernate.org/orm/)

## Build

Project using Gradle to manage dependencies and builds

So you need install Gradle to build this

```bash
./gradlew build
```

Or, Intellij IDEA

## Development

1. Java >= 1.8

2. Database **PostgreSQL**
    
    + One for using app
    
    + One for testing
    
3. Create `.env` (from .env.example)

    ```dotenv
    JDBC_DATABASE_URL=jdbc:postgresql://....
    ```
    
4. Build and run by `./gradlew :run` or run with Intellij IDEA
