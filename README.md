Graduation project of an online internship at TopJava
===============================

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/efbd4c845aa7424e86dc4d14d1124c57)](https://www.codacy.com/gh/MaletyPerun/graduation/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=MaletyPerun/graduation&amp;utm_campaign=Badge_Grade)

-------------------------------------------------------------

Developed Spring Enterprise application with authorization and access rights
role-based using the most popular Java tools and technologies:
Maven, Spring Boot 2.7, Spring Data JPA, Spring Security, REST(Jackson), Lombok, Swagger (REST documentation).
The project uses the H2 database. Attention is paid to errors in requests and their processing.


##  Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) **without frontend**.

-------------------------------------------------------------

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - link to Swagger**).

-------------------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Credentials:
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
