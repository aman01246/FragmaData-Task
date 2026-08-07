
# Google OAuth2 Login with Spring Boot

A simple Spring Boot application that demonstrates Google OAuth2 Login with user registration and profile management using MySQL.


# Application Flow

```text
                 ┌───────────────────────┐
                 │   Home Page ( / )     │
                 │ Login with Google     │
                 └───────────┬───────────┘
                             │
                             ▼
                Click "Login with Google"
                             │
                             ▼
                  Google OAuth Authentication
                             │
                             ▼
                   User selects Google Account
                             │
                             ▼
                 Spring Security Authentication
                             │
                             ▼
                  Redirect to /profile Endpoint
                             │
                             ▼
               Check User Email in MySQL Database
                             │
                ┌────────────┴────────────┐
                │                         │
                ▼                         ▼
        User Exists                User Not Exists
                │                         │
                ▼                         ▼
         Display Profile          Display Registration
                │                 (Name & Email Pre-filled)
                │                         │
                │                         ▼
                │                 User Enters:
                │                 • Phone
                │                 • Department
                │                 • Designation
                │                         │
                │                         ▼
                │                   Save User in MySQL
                │                         │
                └──────────────┬──────────┘
                               │
                               ▼
                       Display Profile Page
                               │
                               ▼
                          Click Logout
                               │
                               ▼
                     Redirect to Home Page
```


##Simple Architecture Diagram
```text
Browser
   │
   ▼
Home Page (Thymeleaf)
   │
   ▼
Spring Security OAuth2
   │
   ▼
Google Authentication
   │
   ▼
UserController
   │
   ▼
UserService
   │
   ▼
UserRepository
   │
   ▼
MySQL Database
```



## Features

- Google OAuth2 Login
- User Registration
- Existing User Detection
- Profile Page
- Logout
- MySQL Database Integration
- Thymeleaf UI

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- OAuth2 Client
- Spring Data JPA
- MySQL
- Thymeleaf
- Maven

## Project Flow

1. Open the Home page.
2. Click **Login with Google**.
3. Authenticate using your Google account.
4. The application checks whether the user    already exists in the database.
5. If the user is new:
   - Show the Registration page.
   - Collect Phone, Department and Designation.
   - Save the user in MySQL.
6. If the user already exists:
   - Open the Profile page directly.
7. User can logout and return to the Home page.

## Database

Database Name: oauth_db
Table: users

Fields:

- id
- google_id
- name
- email
- profile_picture
- phone
- department
- designation

## Author

**Aman Kumar**

GitHub: https://github.com/aman01246/FragmaData-Task
