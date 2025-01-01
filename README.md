# TodoList Application

ToDoList allows adding tasks to a list, editing them, marking them complete and deleting them. ToDoList has simple authentication, and each user has their personal tasks.

This README contains instructions for setting up the TodoList application for local development.

## Prerequisites

Ensure that you have the following installed on your machine:

1. **Java Development Kit (JDK)**: Version 17 or newer.
2. **PostgreSQL**: Ensure the PostgreSQL service is running locally.
3. **Maven**: For managing dependencies and building the application.
4. **Git**: To clone the repository.

## Setup Instructions

### Step 1: Configure the Database

1. Start your PostgreSQL service.
2. Create a PostgreSQL database:

```sql
CREATE DATABASE todolist;
```

3. Create a user with the appropriate credentials:

```sql
CREATE USER list_user WITH PASSWORD 'list_user';
GRANT ALL PRIVILEGES ON DATABASE todolist TO list_user;
```

### Step 2: Build and Run the Application

1. Use Maven to build the application:

```bash
mvn clean install
```

2. Run the application:

```bash
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).


## Additional Notes

- The application uses Hibernate with `ddl-auto=update`, which automatically updates the database schema. For production, ensure to use a safer option like `validate` or `none`.
- Logs will display SQL queries if `spring.jpa.show-sql` is set to `true`. Disable this in production to reduce verbosity.
