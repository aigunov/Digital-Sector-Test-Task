## Digital Sector Test Task - Aigunov Shapi 
#### Overview
- Учебное консольное приложение на Java:
- Модель Employee (инкапсуляция, equals/hashCode/toString по id).
- Бизнес-логика в EmployeeService.
- Работа с файлами (CSV) в FileService.
- Демонстрация в Main.

#### Stack
- Java 25
- Maven
- Junit 6.0

#### Project Structure 
```bash
src/
  main/java/org/example/
    Main.java
    exception/...
    model/Employee.java
    service/EmployeeService.java
    service/FileService.java
  test/java/org/example/service/
    EmployeeServiceTest.java
    FileServiceTest.java
data/               
  pom.xml
```