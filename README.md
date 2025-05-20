🏨 Hotel Booking System

A full-stack Hotel Booking Management System built with Java, Spring Boot, Spring Data JPA, PostgreSQL, and React.js. This project allows customers to explore hotels, check room availability, make bookings, and view billing info. Admins can manage hotels, rooms, and monitor bookings. 💼✨

💻 Tech Stack

Layer

Technology

Backend

Java, Spring Boot, Spring Data JPA

Frontend

React.js

Database

PostgreSQL

Build Tool

Maven

Trigger

PostgreSQL Trigger (on booking insert)

🧹 Core Modules

1. 👤 User Management

Register/Login for users

Roles: Admin and Customer

Admin: Manage hotels and view bookings

Customer: View hotels and make bookings

2. 🏨 Hotel Management

Admin can Add/Update/Delete hotel details:

Name

Location

Room types and prices

Available rooms

3. 🛎️ Room Booking

Customers can:

Select hotel and room type

Choose check-in and check-out dates

System checks availability

If available, booking is confirmed

4. 📖 Booking History & ❌ Cancellation

Customers view previous bookings

Cancel bookings if check-in hasn't started

Admin views all bookings

5. 💸 Billing & Triggers

Auto-generate bill on successful booking

PostgreSQL Trigger inserts a record into the billing table:

Includes booking ID

Total amount

🾃 Database Schema

users(id, name, email, password, role)
hotels(id, name, location)
rooms(id, hotel_id, room_type, price, is_available)
bookings(id, user_id, room_id, check_in, check_out, status)
billing(id, booking_id, amount, generated_at)

🔗 API Endpoints

👤 User

Method

Endpoint

Description

POST

/api/users/register

Register new user

POST

/api/users/login

Login

🏨 Hotels

Method

Endpoint

Description

POST

/api/hotels

Add hotel (Admin only)

GET

/api/hotels

Get all hotels

GET

/api/hotels/{id}

Get hotel by ID

🛎️ Rooms

Method

Endpoint

Description

POST

/api/rooms

Add room (Admin only)

GET

/api/hotels/{hotelId}/rooms

Get rooms by hotel

🗓️ Bookings & 🧾 Billing

Method

Endpoint

Description

POST

/api/booking

Book a room (Customer)

GET

/api/bookings/my

View my bookings (Customer)

DELETE

/api/bookings/{id}

Cancel booking (if allowed)

GET

/api/billings/{bookingId}

View bill for a booking

🔥 PostgreSQL Trigger

A database-level trigger is executed after a booking is inserted:

-- Pseudo trigger example
CREATE OR REPLACE FUNCTION generate_bill()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO billing(booking_id, amount, generated_at)
  VALUES (NEW.id, calculate_total(NEW.room_id, NEW.check_in, NEW.check_out), NOW());
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_booking_insert
AFTER INSERT ON bookings
FOR EACH ROW
EXECUTE FUNCTION generate_bill();

calculate_total() would be a custom function to compute the amount based on room price and stay duration.

🚀 How to Run

Backend

cd backend
mvn spring-boot:run

Frontend

cd frontend
npm install
npm start

Make sure PostgreSQL is up and running, and that your database connection settings in application.properties or .yml are correct. 💡



💖 Author

ISHEMA NKERABAHIZI Love 

📜 License

This project is open-source and free to use for educational purposes.

