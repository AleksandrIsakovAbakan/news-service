# news-service
REST API for news service

# 4_10_practical_work
Консольное приложение "NewsService" разработано в рамках практической работы по курсу Разработка на Spring Framework (раздел
RestAPI с базой данных).
Описание работы приложения:

Команды вводятся с помощью HTTP запросов:
1. Создание пользователей и управление ими (Создание, изменение, просмотр и удаление пользователей разрешается только администратору);
2. Создание категорий новостей и управление ими (Создание, изменение, просмотр и удаление пользователей разрешается только администратору);
3. Создание новостей и управление ими (Редактирование и удаление новости разрешается только тому пользователю, который её создал.);
4. Создание комментариев для новостей и управление ими (Редактирование и удаление комментария к новости разрешается только тому пользователю, который его создал.).
5. Поиск новостей по категориям новости, автору и ID новости.

Параметры API в http://localhost:8080/swagger-ui/index.html

Настройки работы программы осуществляются через изменение application.properties:
app.admin.login=Admin Логин администратора.
app.admin.password=Admin Пароль администратора.
spring.datasource.url=jdbc:postgresql://localhost:5434/postgres Настройка доступа к базе данных.
spring.datasource.username=postgres Логин пользователя для доступа к базе данных.
spring.datasource.password=postgres Пароль пользователя для доступа к базе данных.

