## Flight Booking System

A complete Spring Boot application for airline & flight booking management.

## Overview

This project lets users register airlines, add flight schedules, search flights, book tickets, cancel bookings, and view booking history.

Goals :
# Flight Service REST APIs 

| Method     | Endpoint                                          | Description                                    |
|------------|-----------------------------------------------|----------------------------------------------------|
| **POST**   | `/api/v1.0/flight/airline/inventory/add`      | Adds inventory  for an existing airline            |
| **POST**   | `/api/v1.0/flight/search`                     | Search for flights based on Date,to and from       |
| **POST**   | `/api/v1.0/flight/booking/{flightid}`         | Book a ticket for a specific flight(flightId)      |
| **GET**    | `/api/v1.0/flight/ticket/{pnr}`               | Get ticket/booking details using PNR               |
| **GET**    | `/api/v1.0/flight/booking/history/{emailId}`  | Get all past bookings for a given email            |
| **DELETE** | `/api/v1.0/flight/booking/cancel/{pnr}`       | Cancel a booked ticket using PNR                   |

## Achieved 
1. All the api endpoints are functional <br>
   a.User can register an airline <br>
   b. Add flights to airline based on airline code <br>
   c. Create Bookings based on flightID <br>
   d. Fetch booking using pnr and view booking history using user email <br>
   <br>
2.Added Jmeter stress Test with 20 threads 50 threads and 100 threads 

## Todo:-
1. Add validations and exception handling to do negative testing
2. Adding user authentication feature later
   


━━ 📄 API Documentation
A full CSV file is included in the repo:
api-documentation.csv


            
## DataBase Schema
<img width="903" height="775" alt="image" src="https://github.com/user-attachments/assets/8716c7e5-5164-4056-8659-379caf4f2fcf" />
