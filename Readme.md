# Spring Boot Starter ��� ����������� HTTP ��������

Spring Boot Starter, ������� ������������� ����������� ���������� HTTP ������� � ����� ���������� �� ���� Spring Boot.

���������� �������� � ��������� HTTP ������� � ������ ������ ����������. ������������� ��������� ��� �������� URL ��� �����������. 

����������� �������� � ����: ����� �������, URL, ��������� ������� � ������, ��� ������, ���� ������ � �������, ����� ��������� �������.

���� �������� � ������� ����� ������� **INFO**. ������� ������� � ��� ������ ������������� UUID. ������ ����� ������������ � ������ ������ ����������.
� ���������������� ���������� ������������ **log4j2**. ��������� ������������ � ����� [log4j2-spring.xml](restExample%2Fsrc%2Fmain%2Fresources%2Flog4j2-spring.xml)
[������ ����� ������������ �������](dz4.txt)

## ����������� � ������

��� ����������� �������� �������� ����������� http-log-starter

Maven:

```
<dependency>
    <groupId>ru.petrov</groupId>
    <artifactId>http-log-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
 </dependency>
```

## ��������� ����������� � application.yml

��� ��������� ����������� �������� �������� � ���� application.yml

```yml
http-logger:
  enabled: true
```

��������� url ��� �����������

```yml
http-logger:
  log-url: /example
```

��� ����������� ���� �������� � ������� ������� `/` � �������� log-url
```yml
http-logger:
  log-url: /
```


## ������ ����������������� ����������

� ����� ������������ ������ ��������, ������ ������ �� ��� ������:
- httpLogStarter - ���������� ��� Spring Boot Starter ��� ����������� HTTP ��������
- restExample - ������� REST ���������� � ��� ������������ httpLogStarter � Swagger.

### ��������� ����������
������������� java 21 � maven

### ��������� �������� � ��������� maven �����������

�������� ������ �� ����� master

��������� � ������� ������ ��������
```bash
cd .\httpLogStarter\
```
������ ����������, ��������� � ��������� maven �����������
```bash
mvn clean install
```

### ������ ����-���������� � REST ������������
��������� � ������� ������ 
```bash
cd ..\restExample\
```

��������� ����������
```bash
mvn spring-boot:run
```

### OpenAPI ����-����������

����� ������� ����-���������� ���������� ��������� OpenApi:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)