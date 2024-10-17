# Data Analyzer Application

This project is a **Vaadin-based** Java web application designed for managing and analyzing data, specifically **e-commerce sales data** and **customer data**. The application allows users to view, add, update, delete, and filter data using an intuitive UI built with Vaadin and Java Spring Boot.

## Features

- **Customer Management**:
  - View customer data in a grid.
  - Add new customers.
  - Update existing customer information.
  - Delete customers with confirmation.
  - Filter customer data by name or email.

- **E-commerce Sales Data Management**:
  - View sales data in a grid with various columns like Order ID, Amount, Ship City, Status, and Channel.
  - Add new sales data records.
  - Update existing sales data records.
  - Delete sales data records with confirmation.
  - Filter sales data based on different criteria (Order ID, Status, Ship City, Channel).

## Technologies Used

- **Java 17**
- **Spring Boot** (for dependency injection and application configuration)
- **Vaadin** (for the frontend UI)
- **H2 Database** (for local development)
- **Maven** (for build automation)
- **Lombok** (for reducing boilerplate code)

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK) 17 or later
- Maven
- An IDE like IntelliJ IDEA or Eclipse (recommended for development)

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/data-analyzer.git
   ```

2. **Navigate to the project directory**:

   ```bash
   cd data-analyzer
   ```

3. **Build the project**:

   ```bash
   mvn clean install
   ```

4. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

   The application will be available at [http://localhost:8080](http://localhost:8080).

## Usage

- **Customer Management**:
  - Navigate to `/customers` to manage customer data. You can add new customers using the "Add Customer" button, select an existing customer from the grid to update their details, or delete a customer after confirmation.
  - Use the filter box to search for customers by name or email.

- **E-commerce Sales Data Management**:
  - Navigate to `/ecomSalesData` to manage sales data. You can add new sales records, update existing ones, and delete records.
  - You can filter sales data based on criteria like Order ID, Status, Ship City, and Channel using the filter dropdown and search field.

## Project Structure

```
src
├── main
│   ├── java
│   │   ├── com.acs560.dataanalyzer
│   │   │   ├── models              # Data model classes
│   │   │   ├── services            # Service classes for business logic
│   │   │   ├── views               # Vaadin views and forms for UI
│   │   │   ├── MainLayout.java     # Main layout for the application
│   │   │   ├── Application.java    # Entry point for the Spring Boot application
│   ├── resources
│   │   ├── application.properties  # Application configuration file
│   ├── webapp
│   │   ├── WEB-INF                 # Web configuration files
├── test
│   ├── java
│   │   ├── com.acs560.dataanalyzer # Test classes
```

## Configuration

The application uses an **H2 database** for development. You can configure the database connection in the `src/main/resources/application.properties` file. For production, you can switch to another database by changing the configuration.

### Example Configuration (`application.properties`)

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/EcomSalesData
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA and Hibernate settings
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.defer-datasource-initialization=true

# SQL initialization
spring.sql.init.mode=always
spring.sql.init.platform=mysql
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
```

## Testing

You can run unit and integration tests using:

```bash
mvn test
```

## Contributing

Contributions are welcome! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Contact

For any questions or issues, feel free to contact me at [abroa01@pfw.edu@example.com].

```

### Explanation of the `README.md` Sections:
- **Features**: Lists the capabilities of the application.
- **Technologies Used**: Specifies the tech stack used for development.
- **Installation**: Provides step-by-step instructions for setting up the project locally.
- **Usage**: Explains how to navigate and use different parts of the application.
- **Project Structure**: Outlines the directory structure and what each folder contains.
- **Configuration**: Describes how to configure the application for development and production.
- **Testing**: Explains how to run tests.
- **Contributing**: Details how others can contribute to the project.
- **License**: States the license under which the project is released.
- **Contact**: Provides a way for users to reach out with questions.