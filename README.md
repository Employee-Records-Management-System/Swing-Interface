# Employee Management System

A Java-based Employee Management System that allows you to create, fetch, update, and delete employee records. The system uses **Swing** for the user interface, **Spring Boot** for backend services, and **HTTPClient** for RESTful API communication.

## Features

- View employee details in a dynamic table.
- Add new employees using a modal form.
- Edit employee details.
- Delete employees with confirmation.
- Filter employees by department, status, and search text.
- Displays current user information at the top.

## Technologies Used

- **Java (JDK 11 or later)**: Core programming language.
- **Swing**: User interface library.
- **Spring Boot**: Backend service for handling employee data.
- **Gson**: JSON serialization/deserialization.
- **HTTPClient**: For REST API communication.
- **iText**: For generating PDF reports.

---

## Prerequisites

- Java 11 or later installed.
- Maven installed.
- Backend API running (Spring Boot or similar).
- Environment variable `BASE_URL` set to the backend API's base URL.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Employee-Records-Management-System/Swing-Interface.git
   cd employee-management-system
    ```
2. **Configure the Environment:** Set the BASE_URL system property to your backend's base URL.
 ```bash
   export BASE_URL=http://localhost:8080/api
 ```
3. **Install Dependencies:** Run Maven to download required dependencies.
 ```bash
   mvn clean install
 ```
4. **Run the Application:** Set the BASE_URL system property to your backend's base URL.
 ```bash
   mvn exec:java -Dexec.mainClass="com.hahn.erms.Main"
 ```
## Usage

### Start the Application
- Launch the application and log in with your credentials.

### View Employees
- The table displays all employees fetched from the backend.

### Add Employees
1. Click **"Add Employee"** to open a form.
2. Fill in details like name, department, and hire date.
3. Save the new employee to the backend.

### Delete Employees
1. Select an employee from the table.
2. Click **"Delete Employee"** and confirm the deletion.

### Filter and Search
- Use the top panel to filter employees by department, status, or search text.

### Generate Reports
- Export employee data to a PDF report using the **"Generate PDF"** button.

---

## API Endpoints

The application interacts with the following REST API endpoints:

- **GET** `/employees`: Fetch all employees.
- **GET** `/employees/{id}`: Fetch a specific employee.
- **POST** `/employees`: Create a new employee.
- **DELETE** `/employees/{id}`: Delete an employee.
- **PATCH** `/employees/{id}`: Update an employee.

---

## Classes Overview

### Frontend
- **`EmployeeManagementView`**: Main application window displaying employee data.
- **`AddEmployeeDialog`**: Modal for adding new employees.
- **`TopPanel`**: Panel for filtering and displaying user information.

### Backend Integration
- **`FetcherHelper<K>`**: Generic utility for interacting with the backend API.
- **`EmployeeService`**: Interface for managing employee data.
- **`EmployeeServiceImpl`**: Implementation of `EmployeeService`.

### Utilities
- **`AuthManager`**: Manages user authentication and tokens.
- **`ApiClient`**: Configures and manages HTTP client instances.
- **`LocalDateAdapter`**: Handles serialization and deserialization of `LocalDate` in JSON.

---

## Troubleshooting

### Common Issues

#### DELETE Request Fails
- Ensure the backend API is running and accessible.
- Verify the `BASE_URL` is set correctly.

#### Validation Errors
- Ensure all required fields (e.g., `gender`, `contractType`) are populated.
## Debugging Tips

- **Log Request URLs and Headers**:  
  Verify the correctness of the requests being sent by logging the URL and headers.

  ```java
  System.out.println("Request URL: " + request.uri());
  System.out.println("Authorization Header: " + AuthManager.token);
  ```
- **Use Postman or curl:**
  Test the backend API endpoints manually using tools like Postman or curl to ensure they work as expected.
