# Многопользовательский планировщик задач

## Технологии программной реализации

- Java, Kotlin (языки программирования)
- Maven (фреймворк для сборки проекта)
- Spring Boot, Spring Security, Spring Session, Spring AMQP, Spring Mail, Spring Scheduler (Spring-компоненты)
- PostgreSQL (БД), Spring Data JPA (поддержка БД)
- Redis (хранение сессий)
- Docker (контейнеры, образы, volumes, Dockerfile, Docker Compose)
- RabbitMQ (брокер сообщений для создания очередей)
- CI/CD, GitHub Actions (процесс сборки)

Проект состоит из 3 микросервисов:

### Бэкенд

Java-приложение (Spring Boot), реализующее REST API для работы с пользователями, сессиями и задачами.

Работа с пользователями:

- Регистрация
- Авторизация
- Выход из системы

Работа с задачами:

- Создание и редактирование (задача состоит из заголовка и описания)
- Метка статуса выполнения
- Удаление

### Email-рассылка

Kotlin-приложение (Spring Boot), состоящее из двух модулей: Spring Mail и Spring AMQP.

Чтобы отправить письмо на электронный адрес пользователя, используется модуль Spring Mail для всех полученных сообщений, содержимое которых десериализируется в экземпляр модели.

Приложение подключается к RabbitMQ с помощью Spring AMQP и создает очередь сообщений. Затем подписывается на сообщения от планировщика и бэкенда.

### Планировщик

Kotlin-приложение (Spring Boot), состоящее из двух модулей: Spring Scheduler и Spring AMQP.

Задача сервиса - раз в сутки итерировать всех пользователей, формировать для них отчёты о задачах и изменениях в них за сутки, а также формировать email для отправки. Сформированные сообщения отправляются в RabbitMQ очередь.

## CI/CD
С помощью GitHub Actions была автоматизирована сборка docker образов, загрузка образов в публичный репозиторий https://hub.docker.com/, а также деплой (docker compose) образов на арендованный удаленный сервер. Деплой (CD) происходит автоматически после выполнения процесса сборки и отправки образов в публичный репозиторий (CI).