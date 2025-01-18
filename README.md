# AMC Cinema Project

Welcome to **AMC Cinema**, your ultimate movie ticketing solution! 🎬🍿 This project allows users to browse through movies, view showtimes, and book tickets all in one place. The system also features an admin panel for efficient movie management.

## Project Overview

AMC Cinema is a web-based application designed to simplify the cinema booking experience. It follows the **MVC architecture** and is built with **Java**, **JSP**, **MySQL**, and **HTML/CSS**.

## Key Features

- **User Side:**
  - Browse movies, view trailers, and check showtimes.
  - Secure user registration and login.
  - Make online ticket reservations and view upcoming movies.
  
- **Admin Panel:**
  - Manage movies, including banners and trailers.
  - Add and edit now-showing and upcoming movies.
  - Admin can manage user details and bookings.

## Technology Stack

- **Backend:** Java, Servlets
- **Frontend:** HTML5, CSS, JavaScript
- **Database:** MySQL
- **Web Server:** Apache Tomcat

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/Kavidu23/ABC_Cinema.git
```

2. Set up the database by importing the **amc_cinema.sql** file into MySQL.

3. Run the project in **Apache Tomcat**.

4. Access the site by navigating to:
   ```
   http://localhost:8080/amc-cinema
   ```

## Directory Structure

```
/AMC-Cinema
    /src
        /controller
        /model
        /service
        /util
    /webapp
        /WEB-INF
        /views
            - index.jsp
            - movie.jsp
            - admin.jsp
        /resources
            - css
            - js
    /META-INF
```

## License

This project is licensed under the MIT License.
