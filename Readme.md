# Spring Boot Starter для логирования HTTP запросов

## Запуск приложения
### Требования
Установленный maven

### Установка стартера в локальный maven репозиторий
Перейдите в каталог модуля стартера
```bash
cd .\httpLogStarter\
```
Сборка приложения, установка в локальный maven репозиторий
```bash
mvn clean install
```

### Запуск приложения
Перейдите в каталог модуля 
```bash
cd ..\restExample\
```

Запустите приложение
```bash
mvn spring-boot:run
```



## Включение логирования в application.yml
Для включения логирования добавьте параметр в файл application.yml
```yml
http-logger:
  enabled: true
```
