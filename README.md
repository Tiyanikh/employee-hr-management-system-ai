# Employee HR Management System

A full-stack Human Resources Management System built with Spring Boot, H2 Database, HTML, CSS and JavaScript.

The system is designed to manage employees, departments, leave requests and payroll from one application. It also includes a Smart HR Assistant that can provide information based on the data stored in the system.

## Features

### Employee Management
- Add new employees
- View employee details
- Search employees
- Filter employees by department
- Deactivate employees

### Leave Management
- Submit leave requests
- View leave requests
- Approve leave requests
- Reject leave requests
- Track leave balances
- Validate leave requests before submission

### Payroll Management
- Create payroll records
- Calculate salary information
- View payroll records
- Calculate total payroll

### Dashboard
The dashboard provides an overview of:
- Total employees
- Active employees
- Departments
- Pending leave requests
- Payroll information

### Smart HR Assistant

The application includes a Smart HR Assistant that works with the HR data stored in the system.

It can answer questions such as:

- How many active employees are there?
- Who has pending leave requests?
- What is the total payroll?
- What departments are available?
- Find an employee by name.

The assistant uses the application's Spring Boot services and H2 database rather than requiring an external AI API or API key.

### HR Insights

The dashboard also provides HR insights based on the current system data, such as employee numbers, pending leave requests and payroll information.

## Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- Spring Validation
- H2 Database
- HTML5
- CSS3
- JavaScript
- Maven

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.portfolio.hr
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       └── service
│   │
│   └── resources
│       ├── static
│       │   ├── index.html
│       │   ├── style.css
│       │   └── app.js
│       └── application.properties
│
└── test
    └── java
