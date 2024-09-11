# OAuth Todo App

## Overview

The **OAuth Todo App** is a Spring Boot-based project that allows users to manage their daily tasks. This project uses OAuth 2.0 for secure authentication and authorization, allowing users to log in with their Google or GitHub accounts.

## Features

- **User Authentication**: Login and sign up via Google or GitHub OAuth.
- **Task Management**: Create, update, delete, and view your tasks.
- **Backend API**: RESTful API built with Spring Boot.
- **JWT Token Support**: Secure communication between the client and the server using JWT tokens.

## Tech Stack

- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **Cloud Services**: AWS S3 (for file storage)
- **Authentication**: Google OAuth 2.0, GitHub OAuth 2.0
- **Build Tool**: Maven
- **Version Control**: Git & GitHub

## Getting Started

### Prerequisites

- Java 17
- PostgreSQL
- Maven
- AWS account (for S3 setup)
- Google OAuth credentials (Client ID and Secret)
- GitHub OAuth credentials (Client ID and Secret)

### Installation

#### 1. Backend (Spring Boot)
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/OAuthTodo.git
   cd OAuthTodo
