# Service

### login
* While logging in, the name is being displayed as the email ID.
### addBooking
- While booking, the status is showing as an integer value.
- The user_id is showing as null.


## General Issues
- validations,exceptions are not working
- unnecessary symbol in line 27 of JwtService

## Security Concerns
- Remove db-name,db-username and password.Consider using environment variables or a secure configuration management system.

## Pending Tasks
- Implement unit-testing for `jwt-service`
- Implement appropriate logging and error reporting.
- Implement logic to assign the nearest available taxi while booking.

## Code coverage
- Current code coverage stands at 77%. Aim to increase this by adding more tests and refining existing ones.

#### There is currently no integration with external services like payment gateways.
