# Room Booking System

## Overview
The Room Booking System is a SpringBoot-based REST API that facilitates room bookings for users. It allows users to sign up, log in, book rooms, and manage their bookings. This system ensures that room bookings are efficiently managed by preventing double bookings and handling various edge cases like invalid dates and times.

## Features
1. **User Authentication**:
   - Sign up new users.
   - Log in existing users.

2. **Room Management**:
   - Add, edit, and delete rooms.
   - Retrieve room details.
   - Filter rooms based on date, time, and capacity.

3. **Booking Management**:
   - Create, edit, and delete room bookings.
   - Retrieve booking history and upcoming bookings for a user.

4. **User Management**:
   - Retrieve details of all users.

## API Endpoints

### User Authentication

#### Sign Up
- **URL**: `/signup`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "email": "string",
    "name": "string",
    "password": "string"
  }
  ```
- **Responses**:
  - Account Creation Successful
  - Forbidden, Account already exists

#### Log In
- **URL**: `/login`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Responses**:
  - Login Successful
  - Username/Password Incorrect
  - User does not exist

### User Management

#### Retrieve User Details
- **URL**: `/user`
- **Method**: GET
- **Query Parameter**: `userID=<int>`
- **Response**:
  ```json
  {
    "name": "string",
    "userID": "int",
    "email": "string"
  }
  ```
  - User does not exist

#### Retrieve All Users
- **URL**: `/users`
- **Method**: GET
- **Response**:
  ```json
  [
    {
      "name": "string",
      "userID": "int",
      "email": "string",
      "posts": [
        // list of posts by the user
      ]
    }
  ]
  ```

### Room Management

#### Retrieve Rooms
- **URL**: `/rooms`
- **Method**: GET
- **Query Parameters**: Optional filters on date, time, capacity
- **Response**:
  ```json
  [
    {
      "roomID": "int",
      "roomName": "string",
      "capacity": "int",
      "booked": [
        {
          "bookingID": "int",
          "dateOfBooking": "date",
          "timeFrom": "str",
          "timeTo": "str",
          "purpose": "str",
          "user": {
            "userID": "int"
          }
        }
      ]
    }
  ]
  ```
  - Invalid parameters

#### Add a New Room
- **URL**: `/rooms`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "roomName": "string",
    "roomCapacity": "unsigned int"
  }
  ```
- **Responses**:
  - Room created successfully
  - Room already exists
  - Invalid capacity

#### Edit a Room
- **URL**: `/rooms`
- **Method**: PATCH
- **Request Body**:
  ```json
  {
    "roomID": "int",
    "roomName": "string",
    "roomCapacity": "unsigned int"
  }
  ```
- **Responses**:
  - Room edited successfully
  - Room does not exist
  - Room with given name already exists
  - Invalid capacity

#### Delete a Room
- **URL**: `/rooms`
- **Method**: DELETE
- **Query Parameter**: `roomID=<int>`
- **Responses**:
  - Room deleted successfully
  - Room does not exist

### Booking Management

#### Create a New Booking
- **URL**: `/book`
- **Method**: POST
- **Request Body**:
  ```json
  {
    "userID": "int",
    "roomID": "int",
    "dateOfBooking": "date",
    "timeFrom": "str",
    "timeTo": "str",
    "purpose": "str"
  }
  ```
- **Responses**:
  - Booking created successfully
  - Room unavailable
  - Room does not exist
  - User does not exist
  - Invalid date/time

#### Edit an Existing Booking
- **URL**: `/book`
- **Method**: PATCH
- **Request Body**:
  ```json
  {
    "userID": "int",
    "roomID": "int",
    "bookingID": "int",
    "dateOfBooking": "date",
    "timeFrom": "str",
    "timeTo": "str",
    "purpose": "str"
  }
  ```
- **Responses**:
  - Booking modified successfully
  - Room unavailable
  - Room does not exist
  - User does not exist
  - Booking does not exist
  - Invalid date/time

#### Delete a Booking
- **URL**: `/book`
- **Method**: DELETE
- **Query Parameter**: `bookingID=<int>`
- **Responses**:
  - Booking deleted successfully
  - Booking does not exist

#### Retrieve Room Booking History
- **URL**: `/history`
- **Method**: GET
- **Query Parameter**: `userID=<int>`
- **Response**:
  ```json
  [
    {
      "roomName": "string",
      "roomID": "int",
      "bookingID": "int",
      "dateOfBooking": "date",
      "timeFrom": "str",
      "timeTo": "str",
      "purpose": "str"
    }
  ]
  ```
  - Room does not exist
  - User does not exist

#### Retrieve Upcoming Room Bookings
- **URL**: `/upcoming`
- **Method**: GET
- **Query Parameter**: `userID=<int>`
- **Response**:
  ```json
  [
    {
      "roomName": "string",
      "roomID": "int",
      "bookingID": "int",
      "dateOfBooking": "date",
      "timeFrom": "str",
      "timeTo": "str",
      "purpose": "str"
    }
  ]
  ```
  - User does not exist

## Technologies Used
- **Backend**: SpringBoot
- **Database**: H2
- **IDE**: IntelliJ IDEA
- **API Testing**: Postman

4. **Access the application**:
   - Base URL: `http://127.0.0.1:8080`

5. **API Testing**:
   - Use Postman with the provided Postman collection to test the endpoints.

For any updates or clarifications, please refer to the official documentation.
