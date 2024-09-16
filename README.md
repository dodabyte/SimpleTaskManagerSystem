# Simple Task Manager System
[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-blue.svg)](README.ru.md)

## Description

The project is a backend component of a simple task management system.

## Features

* Ability to create tasks
* Assign different users to tasks
* Indicate the priority and progress of tasks
* Leave comments under the tasks

## Documentation

All detailed information on endpoints is provided in the [Swagger API](http://localhost:9001/swagger-ui/index.html#/).

To run the application in Docker, open the terminal (CMD), go to the project folder and run the `docker-compose up` command. The application itself and the database for the application start in Docker.

All necessary tables will be created in the database automatically using migrations (Liquibase).