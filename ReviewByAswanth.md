## Code Review

### General Comments
- Set the secret key as environment variable.
- It's not a good practice to provide DB username and other security keys in the application.yml file

### UserService
- Remove displaying hashed password in the UserResponse.
- While logging in, the name is being displayed as the email ID.

### Controller class
- If @RequiredArgsConstructor annotation is used, then @Autowired is not needed.

### Authentication
- The API endpoints are functioning without authentication, as JWT-based authentication is enabled.

### Unit tests
- Write tests to check the token validity and expiration in JwtService.
