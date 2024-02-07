# Service

### login
* While logging in, the name is being displayed as the email ID.
### signup
- Handle the exception while giving same email-id in request body
### addBooking
- The user_id is showing as null.
- distance showing null
### Authentication
- The API endpoints are functioning without authentication, as JWT-based authentication is enabled.
### updateBooking
- It's advisable to give the name cancelBooking
- distance,fare,taxiId and userId showing null while update booking

### fareCalculate
- All are showing null in db excluded fare

## General Issues
- validations,exceptions are not working
- unnecessary symbol in line 27 of JwtService

## Security Concerns
- Consider using environment variables or a secure configuration management system.(Regarding db-name,password)

## Pending Tasks
- //Todo : Exception and validation handling
- Implement unit-testing for `jwt-service`
- Implement appropriate logging and error reporting.
- Implement logic to assign the nearest available taxi while booking.


#### There is currently no integration with external services like payment gateways.
