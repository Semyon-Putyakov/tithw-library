SpringProjectNumber2
===================

Веб-приложение на Spring Framework 6.2.0

Описание проекта:
----------------
Это веб-приложение, построенное на Spring Framework с использованием следующих технологий:
- Spring MVC
- Spring Data JPA
- Hibernate
- Thymeleaf
- PostgreSQL

Технический стек:
----------------
- Java
- Spring Framework 6.2.0
- Hibernate 6.2.7.Final
- Thymeleaf 3.1.3.RELEASE
- PostgreSQL
- Maven

Структура проекта:
-----------------
src/
├── main/
│   ├── java/        - исходный код Java
│   ├── resources/   - конфигурационные файлы
│   └── webapp/      - веб-ресурсы

Требования:
----------
- JDK 17 или выше
- Maven 3.6 или выше
- PostgreSQL

Сборка проекта:
--------------
mvn clean install

Запуск проекта:
--------------
После сборки WAR-файл можно развернуть на любом сервере приложений, поддерживающем Jakarta EE 9+ (например, Tomcat 10+) 