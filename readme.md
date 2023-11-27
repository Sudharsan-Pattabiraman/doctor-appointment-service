# **Doctor Appointment Service**

A Spring Boot application for users to book and schedule their doctor appointments.

## Features:
* User can make HTTP requests to book an appointment for their preferred doctor based upon their available time.
* User can update and cancel their appointments using HTTP requests

## Tech Stacks:
Java 8, Spring Boot, H2 In-memory database and Gradle

## Running the Application:
1. Clone the repo from github
2. Locate to the repo 
3. Run `./gradlew clean build bootrun`
4. Application would be accessible at http://localhost:8081
5. Check whether the application is up using health endpoint http://localhost:8081/api/actuator/health in browser
6. To make a new appointment use Postman to make HTTP `POST` call to endpoint http://localhost:8081/api/appointments with request body
   `{
   "patientName":"xyz",
   "doctorName":"Rajesh",
   "appointmentDate":"2023-11-27",
   "appointmentTime":"17:32:00"
   }`
   on successful appointment, the response body will contain a unique `appointmentId` which can be used for updating and checking the appointment status
7. To view the db, navigate to http://localhost:8081/api/h2-console in browser to access h2 console and use following credentials to login into db
   `JDBC url= jdbc:h2:mem:doctor-appointment
    User Name= sa
    password= password`
8. To update an appointment, make HTTP `PUT` call to endpoint http://localhost:8081/api/appointments/{appointmentId} with the appointmentId as PathVariable
   `{
   "doctorName": "doctorName",
   "appointmentDate": "2023-11-30",
   "appointmentTime": "17:32:00"
   }`
    all the fields in UpdateAppointmentRequest is Optional
9. To get/view an appointment, make HTTP `GET` call to endpoint http://localhost:8081/api/appointments/{appointmentId} with the appointmentId as PathVariable
10. To cancel an appointment, make HTTP `PUT` call to endpoint http://localhost:8081/api/appointments/{appointmentId}/cancel with the appointmentId as PathVariable
11. To delete an appointment, make HTTP `DELETE` call to endpoint http://localhost:8081/api/appointments/{appointmentId} with the appointmentId as PathVariable