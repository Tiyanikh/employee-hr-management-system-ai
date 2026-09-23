# Employee HR Management System

## Overview

This is an Employee HR Management System built with Spring Boot, H2 Database, and a simple HTML, CSS and JavaScript frontend.

The system is designed to manage employee information and some common HR processes from one application. It includes employee management, departments, leave requests, attendance information, payroll processing and a dashboard with useful HR statistics.

## Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven
- REST APIs

### Frontend
- HTML5
- CSS3
- JavaScript

### Testing
- JUnit
- Spring Boot Test

## Main Features

### Employee Management
- View employees
- Add new employees
- Update employee details
- Search employees
- Filter employees by department and employment status

### Department Management
- View departments
- Assign employees to departments
- Track department information

### Leave Management
- Submit leave requests
- View leave requests
- Approve leave requests
- Reject leave requests
- Track leave status
- Validate leave dates and available leave days

### Attendance
- Store employee attendance information
- Track attendance records by employee and date

### Payroll
- Process employee payroll
- Calculate gross salary
- Calculate deductions
- Calculate net salary
- Keep payroll records

### Dashboard
The dashboard provides an overview of the system, including:
- Total employees
- Active employees
- Departments
- Pending leave requests
- Payroll information

## Business Rules

The application contains validation rules to prevent incorrect HR data from being saved.

Examples include:

- Employee email addresses must be valid.
- Required employee information must be provided.
- Leave dates must be valid.
- Leave requests cannot use an invalid date range.
- Leave approval is checked against the employee's available leave balance.
- Payroll calculations use the employee's salary and applicable deductions.
- Requests for resources that do not exist return an appropriate error.

## Project Structure

```text
employee-hr-management-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── enviro/
│   │   │           └── hr/
│   │   │               ├── controller/
│   │   │               ├── dto/
│   │   │               ├── entity/
│   │   │               ├── exception/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   └── resources/
│   │       ├── static/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Requirements

Before running the application, make sure the following are installed:

- Java 17 or later
- Maven 3.8 or later
- IntelliJ IDEA or another Java IDE

## Running the Application

### 1. Open the project

Extract the ZIP file and open the project folder in IntelliJ IDEA.

### 2. Build the project

Open the terminal in the project directory and run:

```bash
mvn clean install
```

### 3. Run the application

Run the Spring Boot application from IntelliJ IDEA, or use:

```bash
mvn spring-boot:run
```

### 4. Open the application

Open:

```text
http://localhost:8080
```

## H2 Database

The application uses an in-memory H2 database for development.

H2 Console:

```text
http://localhost:8080/h2-console
```

Use the following connection details:

```text
JDBC URL: jdbc:h2:mem:hrdb
Username: sa
Password:
```

The database is recreated when the application is restarted.

## API Endpoints

### Employees
- `GET /api/employees` - List employees, with optional search and department filters.
- `GET /api/employees/{id}` - Get one employee.
- `POST /api/employees` - Create an employee.
- `PATCH /api/employees/{id}/deactivate` - Deactivate an employee.

### Departments
- `GET /api/departments` - List departments.

### Leave
- `GET /api/leave` - List leave requests.
- `POST /api/leave` - Create a leave request.
- `PATCH /api/leave/{id}/approve` - Approve a request.
- `PATCH /api/leave/{id}/reject` - Reject a request.

### Payroll
- `GET /api/payroll` - List payroll records.
- `POST /api/payroll` - Process payroll.

### Dashboard
- `GET /api/dashboard` - Get dashboard totals.

### Smart HR Assistant
- `POST /api/assistant/ask` - Ask a question using `{ "question": "..." }`.
- `GET /api/assistant/insights` - Get current HR insights.

## Error Handling

The backend uses a global exception handler to return clear responses when something goes wrong.

Examples include:

- Resource not found
- Invalid request data
- Validation errors
- Business rule violations

## Validation

The application validates input before saving information to the database.

Examples include:

- Required fields
- Email format
- Salary values
- Leave dates
- Leave balance
- Employee references

## Database

The application uses Spring Data JPA to communicate with the H2 database.

The main entities include:

- Employee
- Department
- LeaveRequest
- Attendance
- Payroll

Demo data is loaded when the application starts so the dashboard can be tested immediately.

## Testing

Tests are included in the project for the main application functionality.

To run the tests:

```bash
mvn test
```

## Frontend

The frontend is built using standard HTML, CSS and JavaScript.

It communicates with the Spring Boot REST API using HTTP requests and displays the returned data in the browser.

The frontend is served from the Spring Boot application's `static` resources.

## GitHub

The complete project can be placed in a single GitHub repository containing both the backend and frontend.

The main branch should contain the final version of the project.

## Author

Tiyani Khosa

Employee HR Management System

## Smart HR Assistant

The application includes a local HR Assistant that can answer common questions using the current H2 data. It is available from the **AI Assistant** section in the dashboard.

Examples:
- How many active employees are there?
- Who has pending leave?
- What is the total payroll?
- What departments do we have?
- Find an employee by name.

The assistant is implemented in Spring Boot and does not require an external API key. It also provides an automated HR snapshot with active employees, pending leave, recorded payroll and the largest department.

### Assistant API
- `POST /api/assistant/ask` - Ask an HR question using `{ "question": "..." }`.
- `GET /api/assistant/insights` - Get current HR insights.
