Java Enterprise Online Project
===============================
Выпускной проект онлайн стажировки на TopJava 

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/efbd4c845aa7424e86dc4d14d1124c57)](https://www.codacy.com/gh/MaletyPerun/graduation/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MaletyPerun/graduation&amp;utm_campaign=Badge_Grade)

Ссылка на ТЗ: https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md#technical-requirement

Разработано Spring Enterprise приложение c авторизацией и правами доступа
на основе ролей с использованием наиболее популярных инструментов и технологий Java:
Maven, Spring Boot 2.7, Spring Data JPA, Spring Security, REST(Jackson), Lombok, Swagger (REST документация).
В проекте используется БД H2. Уделено внимание ошибкам при запросах и их обработке.

-------------------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Креденшелы:
```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Guest: guest@gmail.com / guest
```
-------------------------------------------------------------

API:

#### get all Users / for admin
`curl -s http://localhost:8080/api/admin/users —user admin@gmail.com:admin`

#### get User 1 / for admin
`curl -s http://localhost:8080/api/admin/users/100001 —user admin@gmail.com:admin`

#### get profile
`curl -s http://localhost:8080/api/profile —user user@yandex.ru:password`

#### register User
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/api/profile`

#### get all Restaurants 
`curl -s http://localhost:8080/api/profile/restaurants —user user@yandex.ru:password`

#### get Restaurant 3 with Meals
`curl -s http://localhost:8080/api/profile/restaurants/3/meals —user user@yandex.ru:password`

#### update Restaurant 1 / for admin
`curl -s -X PUT -d '{"name":"newRestaurant", "address":"Lorem ipsum"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/1 —user admin@gmail.com:admin`

#### create Meal for restaurant 2 / for admin
`curl -s -X POST -d '{"price":"650", "description":"Created lunch"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/2/meals —user admin@gmail.com:admin`

#### delete Restaurant 1 / for admin
`curl -s -X DELETE http://localhost:8080/api/restaurants/1 —user admin@gmail.com:admin`

-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.7, Lombok, H2, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
